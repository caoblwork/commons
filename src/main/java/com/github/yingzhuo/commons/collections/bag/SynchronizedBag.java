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
package com.github.yingzhuo.commons.collections.bag;

import java.util.Set;

import com.github.yingzhuo.commons.collections.collection.SynchronizedCollection;
import com.github.yingzhuo.commons.collections.set.SynchronizedSet;

/**
 * Decorates another <code>Bag</code> to synchronize its behaviour
 * for a multi-threaded environment.
 * <p>
 * Methods are synchronized, then forwarded to the decorated bag.
 * Iterators must be separately synchronized around the loop.
 * <p>
 */
public class SynchronizedBag<E>
        extends SynchronizedCollection<E> implements Bag<E> {

    /** Serialization version */
    private static final long serialVersionUID = 8084674570753837109L;

    /**
     * Factory method to create a synchronized bag.
     * 
     * @param bag  the bag to decorate, must not be null
     * @return a new synchronized Bag
     * @throws IllegalArgumentException if bag is null
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E> Bag<E> decorate(Bag<E> bag) {
        return new SynchronizedBag(bag);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * 
     * @param bag  the bag to decorate, must not be null
     * @throws IllegalArgumentException if bag is null
     */
    protected SynchronizedBag(Bag<E> bag) {
        super(bag);
    }

    /**
     * Gets the bag being decorated.
     * 
     * @return the decorated bag
     */
	protected Bag<E> getBag() {
        return (Bag<E>) collection;
    }
    
    //-----------------------------------------------------------------------
    public boolean add(E object, int count) {
        synchronized (lock) {
            return getBag().add(object, count);
        }
    }

    public boolean remove(E object, int count) {
        synchronized (lock) {
            return getBag().remove(object, count);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<E> uniqueSet() {
        synchronized (lock) {
            Set<E> set = getBag().uniqueSet();
            return new SynchronizedBagSet(set);
        }
    }

    public int getCount(E object) {
        synchronized (lock) {
            return getBag().getCount(object);
        }
    }
    
    //-----------------------------------------------------------------------
    /**
     * Synchronized Set for the Bag class.
     */
    @SuppressWarnings("serial")
	class SynchronizedBagSet<K> extends SynchronizedSet<K> {
        /**
         * Constructor.
         * @param set  the set to decorate
         * @param lock  the lock to use, shared with the bag
         */
        SynchronizedBagSet(Set<K> set) {
            super(set);
        }
    }

}
