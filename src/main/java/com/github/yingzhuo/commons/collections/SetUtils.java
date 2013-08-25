/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.yingzhuo.commons.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import com.github.yingzhuo.commons.collections.collection.SynchronizedSortedSet;
import com.github.yingzhuo.commons.collections.set.ImmutableSet;
import com.github.yingzhuo.commons.collections.set.PredicatedSet;
import com.github.yingzhuo.commons.collections.set.PredicatedSortedSet;
import com.github.yingzhuo.commons.collections.set.SynchronizedSet;
import com.github.yingzhuo.commons.collections.set.TransformedSet;
import com.github.yingzhuo.commons.collections.set.TransformedSortedSet;
import com.github.yingzhuo.commons.collections.set.UnmodifiableSortedSet;
import com.github.yingzhuo.commons.functor.Predicate;
import com.github.yingzhuo.commons.functor.Transformer;

/**
 * Provides utility methods and decorators for
 * {@link Set} and {@link SortedSet} instances.
 *
 */
public class SetUtils {

    /**
     * <code>SetUtils</code> should not normally be instantiated.
     */
    public SetUtils() {
    }

    //-----------------------------------------------------------------------
    /**
     * Tests two sets for equality as per the <code>equals()</code> contract
     * in {@link java.util.Set#equals(java.lang.Object)}.
     * <p>
     * This method is useful for implementing <code>Set</code> when you cannot
     * extend AbstractSet. The method takes Collection instances to enable other
     * collection types to use the Set implementation algorithm.
     * <p>
     * The relevant text (slightly paraphrased as this is a static method) is:
     * <blockquote>
     * <p>Two sets are considered equal if they have
     * the same size, and every member of the first set is contained in
     * the second. This ensures that the <tt>equals</tt> method works
     * properly across different implementations of the <tt>Set</tt>
     * interface.</p>
     * 
     * <p>
     * This implementation first checks if the two sets are the same object: 
     * if so it returns <tt>true</tt>.  Then, it checks if the two sets are
     * identical in size; if not, it returns false. If so, it returns
     * <tt>a.containsAll((Collection) b)</tt>.</p>
     * </blockquote>
     * 
     * @see java.util.Set
     * @param set1  the first set, may be null
     * @param set2  the second set, may be null
     * @return whether the sets are equal by value comparison
     */
    public static <E> boolean isEqualSet(final Collection<E> set1, final Collection<E> set2) {
        if (set1 == set2) {
            return true;
        }
        if (set1 == null || set2 == null || set1.size() != set2.size()) {
            return false;
        }

        return set1.containsAll(set2);
    }

    /**
     * Generates a hash code using the algorithm specified in 
     * {@link java.util.Set#hashCode()}.
     * <p>
     * This method is useful for implementing <code>Set</code> when you cannot
     * extend AbstractSet. The method takes Collection instances to enable other
     * collection types to use the Set implementation algorithm.
     * 
     * @see java.util.Set#hashCode()
     * @param set  the set to calculate the hash code for, may be null
     * @return the hash code
     */
    public static <E> int hashCodeForSet(final Collection<E> set) {
        if (set == null) {
            return 0;
        }
        int hashCode = 0;
        Iterator<E> it = set.iterator();
        Object obj = null;

        while (it.hasNext()) {
            obj = it.next();
            if (obj != null) {
                hashCode += obj.hashCode();
            }
        }
        return hashCode;
    }
    
    //-----------------------------------------------------------------------
    /**
     * Returns a synchronized set backed by the given set.
     * <p>
     * You must manually synchronize on the returned buffer's iterator to 
     * avoid non-deterministic behavior:
     *  
     * <pre>
     * Set s = SetUtils.synchronizedSet(mySet);
     * synchronized (s) {
     *     Iterator i = s.iterator();
     *     while (i.hasNext()) {
     *         process (i.next());
     *     }
     * }
     * </pre>
     * 
     * This method uses the implementation in the decorators subpackage.
     * 
     * @param set  the set to synchronize, must not be null
     * @return a synchronized set backed by the given set
     * @throws IllegalArgumentException  if the set is null
     */
    public static <E> Set<E> synchronizedSet(Set<E> set) {
        return SynchronizedSet.decorate(set);
    }

    /**
     * Returns an unmodifiable set backed by the given set.
     * <p>
     * This method uses the implementation in the decorators subpackage.
     *
     * @param set  the set to make unmodifiable, must not be null
     * @return an unmodifiable set backed by the given set
     * @throws IllegalArgumentException  if the set is null
     */
    public static <E> Set<E> immutableSet(Set<E> set) {
        return ImmutableSet.decorate(set);
    }

    /**
     * Returns a predicated (validating) set backed by the given set.
     * <p>
     * Only objects that pass the test in the given predicate can be added to the set.
     * Trying to add an invalid object results in an IllegalArgumentException.
     * It is important not to use the original set after invoking this method,
     * as it is a backdoor for adding invalid objects.
     *
     * @param set  the set to predicate, must not be null
     * @param predicate  the predicate for the set, must not be null
     * @return a predicated set backed by the given set
     * @throws IllegalArgumentException  if the Set or Predicate is null
     */
    public static <E> Set<E> predicatedSet(Set<E> set, Predicate<E> predicate) {
        return PredicatedSet.decorate(set, predicate);
    }

    /**
     * Returns a transformed set backed by the given set.
     * <p>
     * Each object is passed through the transformer as it is added to the
     * Set. It is important not to use the original set after invoking this 
     * method, as it is a backdoor for adding untransformed objects.
     *
     * @param set  the set to transform, must not be null
     * @param transformer  the transformer for the set, must not be null
     * @return a transformed set backed by the given set
     * @throws IllegalArgumentException  if the Set or Transformer is null
     */
    public static <E> Set<E> transformedSet(Set<E> set, Transformer<E> transformer) {
        return TransformedSet.decorate(set, transformer);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Returns a synchronized sorted set backed by the given sorted set.
     * <p>
     * You must manually synchronize on the returned buffer's iterator to 
     * avoid non-deterministic behavior:
     *  
     * <pre>
     * Set s = SetUtils.synchronizedSet(mySet);
     * synchronized (s) {
     *     Iterator i = s.iterator();
     *     while (i.hasNext()) {
     *         process (i.next());
     *     }
     * }
     * </pre>
     * 
     * This method uses the implementation in the decorators subpackage.
     * 
     * @param set  the sorted set to synchronize, must not be null
     * @return a synchronized set backed by the given set
     * @throws IllegalArgumentException  if the set is null
     */
    public static <E> SortedSet<E> synchronizedSortedSet(SortedSet<E> set) {
        return SynchronizedSortedSet.decorate(set);
    }

    /**
     * Returns an unmodifiable sorted set backed by the given sorted set.
     * <p>
     * This method uses the implementation in the decorators subpackage.
     *
     * @param set  the sorted set to make unmodifiable, must not be null
     * @return an unmodifiable set backed by the given set
     * @throws IllegalArgumentException  if the set is null
     */
    public static <E> SortedSet<E> immutableSet(SortedSet<E> set) {
        return UnmodifiableSortedSet.decorate(set);
    }

    /**
     * Returns a predicated (validating) sorted set backed by the given sorted set.  
     * <p>
     * Only objects that pass the test in the given predicate can be added to the set.
     * Trying to add an invalid object results in an IllegalArgumentException.
     * It is important not to use the original set after invoking this method,
     * as it is a backdoor for adding invalid objects.
     *
     * @param set  the sorted set to predicate, must not be null
     * @param predicate  the predicate for the sorted set, must not be null
     * @return a predicated sorted set backed by the given sorted set
     * @throws IllegalArgumentException  if the Set or Predicate is null
     */
    public static <E> SortedSet<E> predicatedSortedSet(SortedSet<E> set, Predicate<E> predicate) {
        return PredicatedSortedSet.decorate(set, predicate);
    }

    /**
     * Returns a transformed sorted set backed by the given set.
     * <p>
     * Each object is passed through the transformer as it is added to the
     * Set. It is important not to use the original set after invoking this 
     * method, as it is a backdoor for adding untransformed objects.
     *
     * @param set  the set to transform, must not be null
     * @param transformer  the transformer for the set, must not be null
     * @return a transformed set backed by the given set
     * @throws IllegalArgumentException  if the Set or Transformer is null
     */
    public static <E> SortedSet<E> transformedSortedSet(SortedSet<E> set, Transformer<E> transformer) {
        return TransformedSortedSet.decorate(set, transformer);
    }
    
}
