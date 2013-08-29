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
package com.github.yingzhuo.commons.collections.list;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import com.github.yingzhuo.commons.collections.collection.SynchronizedCollection;

/**
 * Decorates another <code>List</code> to synchronize its behaviour
 * for a multi-threaded environment.
 * <p>
 * Methods are synchronized, then forwarded to the decorated list.
 * <p>
 */
public class SynchronizedList<E> extends SynchronizedCollection<E> implements List<E> {

    /** Serialization version */
     private static final long serialVersionUID = -1403835447328619437L;

    /**
     * Factory method to create a synchronized list.
     * 
     * @param list  the list to decorate, must not be null
     * @throws IllegalArgumentException if list is null
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E> List<E> decorate(List<E> list) {
        return new SynchronizedList(list);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * 
     * @param list  the list to decorate, must not be null
     * @throws IllegalArgumentException if list is null
     */
    protected SynchronizedList(List<E> list) {
        super(list);
    }

    /**
     * Gets the decorated list.
     * 
     * @return the decorated list
     */
    protected List<E> getList() {
        return (List<E>) collection;
    }

    //-----------------------------------------------------------------------
    public void add(int index, E object) {
        synchronized (lock) {
            getList().add(index, object);
        }
    }

    public boolean addAll(int index, Collection<? extends E> coll) {
        synchronized (lock) {
            return getList().addAll(index, coll);
        }
    }

    public E get(int index) {
        synchronized (lock) {
            return getList().get(index);
        }
    }

    public int indexOf(Object object) {
        synchronized (lock) {
            return getList().indexOf(object);
        }
    }

    public int lastIndexOf(Object object) {
        synchronized (lock) {
            return getList().lastIndexOf(object);
        }
    }

    /**
     * Iterators must be manually synchronized.
     * <pre>
     * synchronized (coll) {
     *   ListIterator it = coll.listIterator();
     *   // do stuff with iterator
     * }
     * 
     * @return an iterator that must be manually synchronized on the collection
     */
    public ListIterator<E> listIterator() {
        return getList().listIterator();
    }

    /**
     * Iterators must be manually synchronized.
     * <pre>
     * synchronized (coll) {
     *   ListIterator it = coll.listIterator(3);
     *   // do stuff with iterator
     * }
     * 
     * @return an iterator that must be manually synchronized on the collection
     */
    public ListIterator<E> listIterator(int index) {
        return getList().listIterator(index);
    }

    public E remove(int index) {
        synchronized (lock) {
            return getList().remove(index);
        }
    }

    public E set(int index, E object) {
        synchronized (lock) {
            return getList().set(index, object);
        }
    }

    public List<E> subList(int fromIndex, int toIndex) {
        synchronized (lock) {
            List<E> sub = getList().subList(fromIndex, toIndex);
            // the lock is passed into the constructor here to ensure that the sublist is
            // synchronized on the same lock as the parent list
            return SynchronizedList.decorate(sub);
        }
    }

}
