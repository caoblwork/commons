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
import java.util.Iterator;
import java.util.Set;

import com.github.yingzhuo.commons.collections.Bag;
import com.github.yingzhuo.commons.collections.iterator.ImmutableIterator;
import com.github.yingzhuo.commons.collections.set.ImmutableSet;
import com.github.yingzhuo.commons.immutable.Immutable;

/**
 * Decorates another <code>Bag</code> to ensure it can't be altered.
 */
public final class ImmutableBag<E>
        extends AbstractBagDecorator<E> implements Immutable, Serializable {

    /** Serialization version */
    private static final long serialVersionUID = -1873799975157099624L;

    /**
     * Factory method to create an unmodifiable bag.
     * <p>
     * If the bag passed in is already unmodifiable, it is returned.
     * 
     * @param bag  the bag to decorate, must not be null
     * @return an unmodifiable Bag
     * @throws IllegalArgumentException if bag is null
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E> Bag<E> decorate(Bag<E> bag) {
        if (bag instanceof Immutable) {
            return bag;
        }
        return new ImmutableBag(bag);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * 
     * @param bag  the bag to decorate, must not be null
     * @throws IllegalArgumentException if bag is null
     */
    private ImmutableBag(Bag<E> bag) {
        super(bag);
    }

    //-----------------------------------------------------------------------
    /**
     * Write the collection out using a custom routine.
     * 
     * @param out  the output stream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(collection);
    }

    /**
     * Read the collection in using a custom routine.
     * 
     * @param in  the input stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        collection = (Collection) in.readObject();
    }

    //-----------------------------------------------------------------------
    public Iterator<E> iterator() {
        return ImmutableIterator.decorate(getCollection().iterator());
    }

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
    public boolean add(Object object, int count) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object object, int count) {
        throw new UnsupportedOperationException();
    }

    public Set<E> uniqueSet() {
        Set<E> set = getBag().uniqueSet();
        return ImmutableSet.decorate(set);
    }

}
