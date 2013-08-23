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
package com.github.yingzhuo.commons.collections.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Decorates another <code>Collection</code> to synchronize its behaviour
 * for a multi-threaded environment.
 * <p>
 * Iterators must be manually synchronized:
 * <pre>
 * synchronized (coll) {
 *   Iterator it = coll.iterator();
 *   // do stuff with iterator
 * }
 * </pre>
 * </p>
 */
public class SynchronizedCollection<E> implements Collection<E>, Serializable {

    /** Serialization version */
    private static final long serialVersionUID = 2412805092710877986L;

    /** The collection to decorate */
    protected final Collection<E> collection;
    /** The object to lock on, needed for List/SortedSet views */
    protected final Object lock;

    /**
     * Factory method to create a synchronized collection.
     * 
     * @param coll  the collection to decorate, must not be null
     * @return a new synchronized collection
     * @throws IllegalArgumentException if collection is null
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E> Collection<E> decorate(Collection<E> coll) {
        return new SynchronizedCollection(coll);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * 
     * @param collection  the collection to decorate, must not be null
     * @throws IllegalArgumentException if the collection is null
     */
    protected SynchronizedCollection(Collection<E> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection must not be null");
        }
        this.collection = collection;
        this.lock = this;
    }

    //-----------------------------------------------------------------------
    public boolean add(E object) {
        synchronized (lock) {
            return collection.add(object);
        }
    }

    public boolean addAll(Collection<? extends E> coll) {
        synchronized (lock) {
            return collection.addAll(coll);
        }
    }

    public void clear() {
        synchronized (lock) {
            collection.clear();
        }
    }

    public boolean contains(Object object) {
        synchronized (lock) {
            return collection.contains(object);
        }
    }

    public boolean containsAll(Collection<?> coll) {
        synchronized (lock) {
            return collection.containsAll(coll);
        }
    }

    public boolean isEmpty() {
        synchronized (lock) {
            return collection.isEmpty();
        }
    }

    /**
     * Iterators must be manually synchronized.
     * <pre>
     * synchronized (coll) {
     *   Iterator it = coll.iterator();
     *   // do stuff with iterator
     * }
     * 
     * @return an iterator that must be manually synchronized on the collection
     */
    public Iterator<E> iterator() {
        return collection.iterator();
    }

    public Object[] toArray() {
        synchronized (lock) {
            return collection.toArray();
        }
    }

    public <T> T[] toArray(T[] object) {
        synchronized (lock) {
            return collection.toArray(object);
        }
    }

    public boolean remove(Object object) {
        synchronized (lock) {
            return collection.remove(object);
        }
    }

    public boolean removeAll(Collection<?> coll) {
        synchronized (lock) {
            return collection.removeAll(coll);
        }
    }

    public boolean retainAll(Collection<?> coll) {
        synchronized (lock) {
            return collection.retainAll(coll);
        }
    }

    public int size() {
        synchronized (lock) {
            return collection.size();
        }
    }

    public boolean equals(Object object) {
        synchronized (lock) {
            if (object == this) {
                return true;
            }
            return collection.equals(object);
        }
    }

    public int hashCode() {
        synchronized (lock) {
            return collection.hashCode();
        }
    }

    public String toString() {
        synchronized (lock) {
            return collection.toString();
        }
    }

}
