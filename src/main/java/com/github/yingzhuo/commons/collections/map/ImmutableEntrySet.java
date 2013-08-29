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
package com.github.yingzhuo.commons.collections.map;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.github.yingzhuo.commons.collections.iterator.AbstractIteratorDecorator;
import com.github.yingzhuo.commons.collections.set.AbstractSetDecorator;
import com.github.yingzhuo.commons.immutable.Immutable;

/**
 * Decorates a map entry <code>Set</code> to ensure it can't be altered.
 */
public final class ImmutableEntrySet<E>
        extends AbstractSetDecorator<E> implements Immutable {

    /**
     * Factory method to create an unmodifiable set of Map Entry objects.
     * 
     * @param set  the set to decorate, must not be null
     * @throws IllegalArgumentException if set is null
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E> Set<E> decorate(Set<E> set) {
        if (set instanceof Immutable) {
            return set;
        }
        return new ImmutableEntrySet(set);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * 
     * @param set  the set to decorate, must not be null
     * @throws IllegalArgumentException if set is null
     */
    private ImmutableEntrySet(Set<E> set) {
        super(set);
    }

    //-----------------------------------------------------------------------
    public boolean add(Object object) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends E> coll) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> coll) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> coll) {
        throw new UnsupportedOperationException();
    }

    //-----------------------------------------------------------------------
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Iterator<E> iterator() {
        return new UnmodifiableEntrySetIterator(collection.iterator());
    }
    
    @SuppressWarnings({"rawtypes"})
    public Object[] toArray() {
        Object[] array = collection.toArray();
        for (int i = 0; i < array.length; i++) {
            array[i] = new UnmodifiableEntry((Map.Entry) array[i]);
        }
        return array;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T[] toArray(T array[]) {
        T[] result = array;
        if (array.length > 0) {
            // we must create a new array to handle multi-threaded situations
            // where another thread could access data before we decorate it
            result = (T[]) Array.newInstance(array.getClass().getComponentType(), 0);
        }
        result = collection.toArray(result);
        for (int i = 0; i < result.length; i++) {
            result[i] = (T) new UnmodifiableEntry((Map.Entry) result[i]);
        }

        // check to see if result should be returned straight
        if (result.length > array.length) {
            return result;
        }

        // copy back into input array to fulfil the method contract
        System.arraycopy(result, 0, array, 0, result.length);
        if (array.length > result.length) {
            array[result.length] = null;
        }
        return array;
    }
    
    //-----------------------------------------------------------------------
    /**
     * Implementation of an entry set iterator.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    final static class UnmodifiableEntrySetIterator<E> extends AbstractIteratorDecorator<E> {
        
        protected UnmodifiableEntrySetIterator(Iterator<E> iterator) {
            super(iterator);
        }
        
        public E next() {
            Map.Entry entry = (Map.Entry) iterator.next();
            return (E) new UnmodifiableEntry(entry);
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Implementation of a map entry that is unmodifiable.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
	final static class UnmodifiableEntry extends AbstractMapEntryDecorator implements Map.Entry {

		protected UnmodifiableEntry(Map.Entry entry) {
            super(entry);
        }

        public Object setValue(Object obj) {
            throw new UnsupportedOperationException();
        }
    }

}
