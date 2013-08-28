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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

import com.github.yingzhuo.commons.collections.SortedBag;

/**
 * Implements <code>SortedBag</code>, using a <code>TreeMap</code> to provide
 * the data storage. This is the standard implementation of a sorted bag.
 * <p>
 * Order will be maintained among the bag members and can be viewed through the
 * iterator.
 * <p>
 * A <code>Bag</code> stores each object in the collection together with a
 * count of occurrences. Extra methods on the interface allow multiple copies
 * of an object to be added or removed at once. It is important to read the
 * interface javadoc carefully as several methods violate the
 * <code>Collection</code> interface specification.
 *
 */
public class TreeBag<E>
        extends AbstractMapBag<E> implements SortedBag<E>, Serializable {

    /** Serial version lock */
    private static final long serialVersionUID = -7740146511091606676L;
    
    /**
     * Constructs an empty <code>TreeBag</code>.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public TreeBag() {
        super(new TreeMap());
    }

    /**
     * Constructs an empty bag that maintains order on its unique
     * representative members according to the given {@link Comparator}.
     * 
     * @param comparator  the comparator to use
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public TreeBag(Comparator<? super E> comparator) {
        super(new TreeMap(comparator));
    }

    /**
     * Constructs a <code>TreeBag</code> containing all the members of the
     * specified collection.
     * 
     * @param coll  the collection to copy into the bag
     */
    public TreeBag(Collection<? extends E> coll) {
        this();
        addAll(coll);
    }

    //-----------------------------------------------------------------------
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public E first() {
        return (E) ((SortedMap) getMap()).firstKey();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public E last() {
        return (E) ((SortedMap) getMap()).lastKey();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Comparator<? super E> comparator() {
        return ((SortedMap) getMap()).comparator();
    }

    //-----------------------------------------------------------------------
    /**
     * Write the bag out using a custom routine.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(comparator());
        super.doWriteObject(out);
    }

    /**
     * Read the bag in using a custom routine.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        Comparator comp = (Comparator) in.readObject();
        super.doReadObject(new TreeMap(comp), in);
    }
    
}