/*
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.yingzhuo.commons.collections;

import static com.github.yingzhuo.commons.collections.CollectPreconditions.checkNonnegative;
import static com.github.yingzhuo.commons.collections.CollectPreconditions.checkRemove;
import static guava.base.Preconditions.checkArgument;
import static guava.base.Preconditions.checkNotNull;

import com.github.yingzhuo.commons.collections.Serialization.FieldSetter;
import com.github.yingzhuo.commons.lang.primitive.Ints;
import com.github.yingzhuo.commons.math.IntMath;

import guava.annotations.Beta;
import guava.annotations.VisibleForTesting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

/**
 * A multiset that supports concurrent modifications and that provides atomic versions of most
 * {@code Multiset} operations (exceptions where noted). Null elements are not supported.
 *
 * <p>See the Guava User Guide article on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/NewCollectionTypesExplained#Multiset">
 * {@code Multiset}</a>.
 *
 * @author Cliff L. Biffle
 * @author mike nonemacher
 * @since 2.0 (imported from Google Collections Library)
 */
public final class ConcurrentHashMultiset<E> extends AbstractMultiset<E> implements Serializable {

  /*
   * The ConcurrentHashMultiset's atomic operations are implemented primarily in terms of
   * AtomicInteger's atomic operations, with some help from ConcurrentMap's atomic operations on
   * creation and removal (including automatic removal of zeroes). If the modification of an
   * AtomicInteger results in zero, we compareAndSet the value to zero; if that succeeds, we remove
   * the entry from the Map. If another operation sees a zero in the map, it knows that the entry is
   * about to be removed, so this operation may remove it (often by replacing it with a new
   * AtomicInteger).
   */

  /** The number of occurrences of each element. */
  private final transient ConcurrentMap<E, AtomicInteger> countMap;

  // This constant allows the deserialization code to set a final field. This holder class
  // makes sure it is not initialized unless an instance is deserialized.
  private static class FieldSettersHolder {
    @SuppressWarnings("rawtypes")
	static final FieldSetter<ConcurrentHashMultiset> COUNT_MAP_FIELD_SETTER =
        Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");
  }

  /**
   * Creates a new, empty {@code ConcurrentHashMultiset} using the default
   * initial capacity, load factor, and concurrency settings.
   */
  public static <E> ConcurrentHashMultiset<E> create() {
    // TODO(schmoe): provide a way to use this class with other (possibly arbitrary)
    // ConcurrentMap implementors. One possibility is to extract most of this class into
    // an AbstractConcurrentMapMultiset.
    return new ConcurrentHashMultiset<E>(new ConcurrentHashMap<E, AtomicInteger>());
  }

  /**
   * Creates a new {@code ConcurrentHashMultiset} containing the specified elements, using
   * the default initial capacity, load factor, and concurrency settings.
   *
   * <p>This implementation is highly efficient when {@code elements} is itself a {@link Multiset}.
   *
   * @param elements the elements that the multiset should contain
   */
  public static <E> ConcurrentHashMultiset<E> create(Iterable<? extends E> elements) {
    ConcurrentHashMultiset<E> multiset = ConcurrentHashMultiset.create();
    Iterables.addAll(multiset, elements);
    return multiset;
  }

  /**
   * Creates a new, empty {@code ConcurrentHashMultiset} using {@code mapMaker}
   * to construct the internal backing map.
   *
   * <p>If this {@link MapMaker} is configured to use entry eviction of any kind, this eviction
   * applies to all occurrences of a given element as a single unit. However, most updates to the
   * multiset do not count as map updates at all, since we're usually just mutating the value
   * stored in the map, so {@link MapMaker#expireAfterAccess} makes sense (evict the entry that
   * was queried or updated longest ago), but {@link MapMaker#expireAfterWrite} doesn't, because
   * the eviction time is measured from when we saw the first occurrence of the object.
   *
   * <p>The returned multiset is serializable but any serialization caveats
   * given in {@code MapMaker} apply.
   *
   * <p>Finally, soft/weak values can be used but are not very useful: the values are created
   * internally and not exposed externally, so no one else will have a strong reference to the
   * values. Weak keys on the other hand can be useful in some scenarios.
   *
   * @since 15.0 (source compatible (accepting the since removed {@code GenericMapMaker} class)
   *     since 7.0)
   */
  @Beta
  public static <E> ConcurrentHashMultiset<E> create(MapMaker mapMaker) {
    return new ConcurrentHashMultiset<E>(mapMaker.<E, AtomicInteger>makeMap());
  }

  /**
   * Creates an instance using {@code countMap} to store elements and their counts.
   *
   * <p>This instance will assume ownership of {@code countMap}, and other code
   * should not maintain references to the map or modify it in any way.
   *
   * @param countMap backing map for storing the elements in the multiset and
   *     their counts. It must be empty.
   * @throws IllegalArgumentException if {@code countMap} is not empty
   */
  @VisibleForTesting ConcurrentHashMultiset(ConcurrentMap<E, AtomicInteger> countMap) {
    checkArgument(countMap.isEmpty());
    this.countMap = countMap;
  }

  // Query Operations

  /**
   * Returns the number of occurrences of {@code element} in this multiset.
   *
   * @param element the element to look for
   * @return the nonnegative number of occurrences of the element
   */
  @Override public int count(@Nullable Object element) {
    AtomicInteger existingCounter = Maps.safeGet(countMap, element);
    return (existingCounter == null) ? 0 : existingCounter.get();
  }

  /**
   * {@inheritDoc}
   *
   * <p>If the data in the multiset is modified by any other threads during this method,
   * it is undefined which (if any) of these modifications will be reflected in the result.
   */
  @Override public int size() {
    long sum = 0L;
    for (AtomicInteger value : countMap.values()) {
      sum += value.get();
    }
    return Ints.saturatedCast(sum);
  }

  /*
   * Note: the superclass toArray() methods assume that size() gives a correct
   * answer, which ours does not.
   */

  @Override public Object[] toArray() {
    return snapshot().toArray();
  }

  @Override public <T> T[] toArray(T[] array) {
    return snapshot().toArray(array);
  }

  /*
   * We'd love to use 'new ArrayList(this)' or 'list.addAll(this)', but
   * either of these would recurse back to us again!
   */
  private List<E> snapshot() {
    List<E> list = Lists.newArrayListWithExpectedSize(size());
    for (Multiset.Entry<E> entry : entrySet()) {
      E element = entry.getElement();
      for (int i = entry.getCount(); i > 0; i--) {
        list.add(element);
      }
    }
    return list;
  }

  // Modification Operations

  /**
   * Adds a number of occurrences of the specified element to this multiset.
   *
   * @param element the element to add
   * @param occurrences the number of occurrences to add
   * @return the previous count of the element before the operation; possibly zero
   * @throws IllegalArgumentException if {@code occurrences} is negative, or if
   *     the resulting amount would exceed {@link Integer#MAX_VALUE}
   */
  @Override public int add(E element, int occurrences) {
    checkNotNull(element);
    if (occurrences == 0) {
      return count(element);
    }
    checkArgument(occurrences > 0, "Invalid occurrences: %s", occurrences);

    while (true) {
      AtomicInteger existingCounter = Maps.safeGet(countMap, element);
      if (existingCounter == null) {
        existingCounter = countMap.putIfAbsent(element, new AtomicInteger(occurrences));
        if (existingCounter == null) {
          return 0;
        }
        // existingCounter != null: fall through to operate against the existing AtomicInteger
      }

      while (true) {
        int oldValue = existingCounter.get();
        if (oldValue != 0) {
          try {
            int newValue = IntMath.checkedAdd(oldValue, occurrences);
            if (existingCounter.compareAndSet(oldValue, newValue)) {
              // newValue can't == 0, so no need to check & remove
              return oldValue;
            }
          } catch (ArithmeticException overflow) {
            throw new IllegalArgumentException("Overflow adding " + occurrences
                + " occurrences to a count of " + oldValue);
          }
        } else {
          // In the case of a concurrent remove, we might observe a zero value, which means another
          // thread is about to remove (element, existingCounter) from the map. Rather than wait,
          // we can just do that work here.
          AtomicInteger newCounter = new AtomicInteger(occurrences);
          if ((countMap.putIfAbsent(element, newCounter) == null)
              || countMap.replace(element, existingCounter, newCounter)) {
            return 0;
          }
          break;
        }
      }

      // If we're still here, there was a race, so just try again.
    }
  }

  /**
   * Removes a number of occurrences of the specified element from this multiset. If the multiset
   * contains fewer than this number of occurrences to begin with, all occurrences will be removed.
   *
   * @param element the element whose occurrences should be removed
   * @param occurrences the number of occurrences of the element to remove
   * @return the count of the element before the operation; possibly zero
   * @throws IllegalArgumentException if {@code occurrences} is negative
   */
  /*
   * TODO(cpovirk): remove and removeExactly currently accept null inputs only
   * if occurrences == 0. This satisfies both NullPointerTester and
   * CollectionRemoveTester.testRemove_nullAllowed, but it's not clear that it's
   * a good policy, especially because, in order for the test to pass, the
   * parameter must be misleadingly annotated as @Nullable. I suspect that
   * we'll want to remove @Nullable, add an eager checkNotNull, and loosen up
   * testRemove_nullAllowed.
   */
  @Override public int remove(@Nullable Object element, int occurrences) {
    if (occurrences == 0) {
      return count(element);
    }
    checkArgument(occurrences > 0, "Invalid occurrences: %s", occurrences);

    AtomicInteger existingCounter = Maps.safeGet(countMap, element);
    if (existingCounter == null) {
      return 0;
    }
    while (true) {
      int oldValue = existingCounter.get();
      if (oldValue != 0) {
        int newValue = Math.max(0, oldValue - occurrences);
        if (existingCounter.compareAndSet(oldValue, newValue)) {
          if (newValue == 0) {
            // Just CASed to 0; remove the entry to clean up the map. If the removal fails,
            // another thread has already replaced it with a new counter, which is fine.
            countMap.remove(element, existingCounter);
          }
          return oldValue;
        }
      } else {
        return 0;
      }
    }
  }

  /**
   * Removes exactly the specified number of occurrences of {@code element}, or makes no
   * change if this is not possible.
   *
   * <p>This method, in contrast to {@link #remove(Object, int)}, has no effect when the
   * element count is smaller than {@code occurrences}.
   *
   * @param element the element to remove
   * @param occurrences the number of occurrences of {@code element} to remove
   * @return {@code true} if the removal was possible (including if {@code occurrences} is zero)
   */
  public boolean removeExactly(@Nullable Object element, int occurrences) {
    if (occurrences == 0) {
      return true;
    }
    checkArgument(occurrences > 0, "Invalid occurrences: %s", occurrences);

    AtomicInteger existingCounter = Maps.safeGet(countMap, element);
    if (existingCounter == null) {
      return false;
    }
    while (true) {
      int oldValue = existingCounter.get();
      if (oldValue < occurrences) {
        return false;
      }
      int newValue = oldValue - occurrences;
      if (existingCounter.compareAndSet(oldValue, newValue)) {
        if (newValue == 0) {
          // Just CASed to 0; remove the entry to clean up the map. If the removal fails,
          // another thread has already replaced it with a new counter, which is fine.
          countMap.remove(element, existingCounter);
        }
        return true;
      }
    }
  }

  /**
   * Adds or removes occurrences of {@code element} such that the {@link #count} of the
   * element becomes {@code count}.
   *
   * @return the count of {@code element} in the multiset before this call
   * @throws IllegalArgumentException if {@code count} is negative
   */
  @Override public int setCount(E element, int count) {
    checkNotNull(element);
    checkNonnegative(count, "count");
    while (true) {
      AtomicInteger existingCounter = Maps.safeGet(countMap, element);
      if (existingCounter == null) {
        if (count == 0) {
          return 0;
        } else {
          existingCounter = countMap.putIfAbsent(element, new AtomicInteger(count));
          if (existingCounter == null) {
            return 0;
          }
          // existingCounter != null: fall through
        }
      }

      while (true) {
        int oldValue = existingCounter.get();
        if (oldValue == 0) {
          if (count == 0) {
            return 0;
          } else {
            AtomicInteger newCounter = new AtomicInteger(count);
            if ((countMap.putIfAbsent(element, newCounter) == null)
                || countMap.replace(element, existingCounter, newCounter)) {
              return 0;
            }
          }
          break;
        } else {
          if (existingCounter.compareAndSet(oldValue, count)) {
            if (count == 0) {
              // Just CASed to 0; remove the entry to clean up the map. If the removal fails,
              // another thread has already replaced it with a new counter, which is fine.
              countMap.remove(element, existingCounter);
            }
            return oldValue;
          }
        }
      }
    }
  }

  /**
   * Sets the number of occurrences of {@code element} to {@code newCount}, but only if
   * the count is currently {@code expectedOldCount}. If {@code element} does not appear
   * in the multiset exactly {@code expectedOldCount} times, no changes will be made.
   *
   * @return {@code true} if the change was successful. This usually indicates
   *     that the multiset has been modified, but not always: in the case that
   *     {@code expectedOldCount == newCount}, the method will return {@code true} if
   *     the condition was met.
   * @throws IllegalArgumentException if {@code expectedOldCount} or {@code newCount} is negative
   */
  @Override public boolean setCount(E element, int expectedOldCount, int newCount) {
    checkNotNull(element);
    checkNonnegative(expectedOldCount, "oldCount");
    checkNonnegative(newCount, "newCount");

    AtomicInteger existingCounter = Maps.safeGet(countMap, element);
    if (existingCounter == null) {
      if (expectedOldCount != 0) {
        return false;
      } else if (newCount == 0) {
        return true;
      } else {
        // if our write lost the race, it must have lost to a nonzero value, so we can stop
        return countMap.putIfAbsent(element, new AtomicInteger(newCount)) == null;
      }
    }
    int oldValue = existingCounter.get();
    if (oldValue == expectedOldCount) {
      if (oldValue == 0) {
        if (newCount == 0) {
          // Just observed a 0; try to remove the entry to clean up the map
          countMap.remove(element, existingCounter);
          return true;
        } else {
          AtomicInteger newCounter = new AtomicInteger(newCount);
          return (countMap.putIfAbsent(element, newCounter) == null)
              || countMap.replace(element, existingCounter, newCounter);
        }
      } else {
        if (existingCounter.compareAndSet(oldValue, newCount)) {
          if (newCount == 0) {
            // Just CASed to 0; remove the entry to clean up the map. If the removal fails,
            // another thread has already replaced it with a new counter, which is fine.
            countMap.remove(element, existingCounter);
          }
          return true;
        }
      }
    }
    return false;
  }

  // Views

  @Override Set<E> createElementSet() {
    final Set<E> delegate = countMap.keySet();
    return new ForwardingSet<E>() {
      @Override protected Set<E> delegate() {
        return delegate;
      }

      @Override
      public boolean contains(@Nullable Object object) {
        return object != null && Collections2.safeContains(delegate, object);
      }

      @Override
      public boolean containsAll(Collection<?> collection) {
        return standardContainsAll(collection);
      }

      @Override public boolean remove(Object object) {
        return object != null && Collections2.safeRemove(delegate, object);
      }

      @Override public boolean removeAll(Collection<?> c) {
        return standardRemoveAll(c);
      }
    };
  }

  private transient EntrySet entrySet;

  @Override public Set<Multiset.Entry<E>> entrySet() {
    EntrySet result = entrySet;
    if (result == null) {
      entrySet = result = new EntrySet();
    }
    return result;
  }

  @Override int distinctElements() {
    return countMap.size();
  }

  @Override public boolean isEmpty() {
    return countMap.isEmpty();
  }

  @Override Iterator<Entry<E>> entryIterator() {
    // AbstractIterator makes this fairly clean, but it doesn't support remove(). To support
    // remove(), we create an AbstractIterator, and then use ForwardingIterator to delegate to it.
    final Iterator<Entry<E>> readOnlyIterator =
        new AbstractIterator<Entry<E>>() {
          private Iterator<Map.Entry<E, AtomicInteger>> mapEntries = countMap.entrySet().iterator();

          @Override protected Entry<E> computeNext() {
            while (true) {
              if (!mapEntries.hasNext()) {
                return endOfData();
              }
              Map.Entry<E, AtomicInteger> mapEntry = mapEntries.next();
              int count = mapEntry.getValue().get();
              if (count != 0) {
                return Multisets.immutableEntry(mapEntry.getKey(), count);
              }
            }
          }
        };

    return new ForwardingIterator<Entry<E>>() {
      private Entry<E> last;

      @Override protected Iterator<Entry<E>> delegate() {
        return readOnlyIterator;
      }

      @Override public Entry<E> next() {
        last = super.next();
        return last;
      }

      @Override public void remove() {
        checkRemove(last != null);
        ConcurrentHashMultiset.this.setCount(last.getElement(), 0);
        last = null;
      }
    };
  }

  @Override public void clear() {
    countMap.clear();
  }

  private class EntrySet extends AbstractMultiset<E>.EntrySet {
    @Override ConcurrentHashMultiset<E> multiset() {
      return ConcurrentHashMultiset.this;
    }

    /*
     * Note: the superclass toArray() methods assume that size() gives a correct
     * answer, which ours does not.
     */

    @Override public Object[] toArray() {
      return snapshot().toArray();
    }

    @Override public <T> T[] toArray(T[] array) {
      return snapshot().toArray(array);
    }

    private List<Multiset.Entry<E>> snapshot() {
      List<Multiset.Entry<E>> list = Lists.newArrayListWithExpectedSize(size());
      // Not Iterables.addAll(list, this), because that'll forward right back here.
      Iterators.addAll(list, iterator());
      return list;
    }
  }

  /**
   * @serialData the ConcurrentMap of elements and their counts.
   */
  private void writeObject(ObjectOutputStream stream) throws IOException {
    stream.defaultWriteObject();
    stream.writeObject(countMap);
  }

  private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
    stream.defaultReadObject();
    @SuppressWarnings("unchecked") // reading data stored by writeObject
    ConcurrentMap<E, Integer> deserializedCountMap =
        (ConcurrentMap<E, Integer>) stream.readObject();
    FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set(this, deserializedCountMap);
  }

  private static final long serialVersionUID = 1;
}
