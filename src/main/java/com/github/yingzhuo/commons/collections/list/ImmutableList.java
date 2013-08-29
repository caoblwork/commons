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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.github.yingzhuo.commons.collections.iterator.ImmutableIterator;
import com.github.yingzhuo.commons.collections.iterator.ImmutableListIterator;
import com.github.yingzhuo.commons.immutable.Immutable;

/**
 * Decorates another <code>List</code> to ensure it can't be altered.
 */
public final class ImmutableList<E>
        extends AbstractSerializableListDecorator<E>
        implements Immutable {

    /** Serialization version */
    private static final long serialVersionUID = 6595182819922443652L;

    /**
     * Factory method to create an unmodifiable list.
     * 
     * @param list  the list to decorate, must not be null
     * @throws IllegalArgumentException if list is null
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E> List<E> decorate(List<E> list) {
        if (list instanceof Immutable) {
            return list;
        }
        return new ImmutableList(list);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * 
     * @param list  the list to decorate, must not be null
     * @throws IllegalArgumentException if list is null
     */
    private ImmutableList(List<E> list) {
        super(list);
    }

    //-----------------------------------------------------------------------
    public Iterator<E> iterator() {
        return ImmutableIterator.decorate(getCollection().iterator());
    }

    public boolean add(E object) {
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
    public ListIterator<E> listIterator() {
        return ImmutableListIterator.decorate(getList().listIterator());
    }

    public ListIterator<E> listIterator(int index) {
        return ImmutableListIterator.decorate(getList().listIterator(index));
    }

    public void add(int index, E object) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int index, Collection<? extends E> coll) {
        throw new UnsupportedOperationException();
    }

    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    public E set(int index, E object) {
        throw new UnsupportedOperationException();
    }

    public List<E> subList(int fromIndex, int toIndex) {
        List<E> sub = getList().subList(fromIndex, toIndex);
        return ImmutableList.decorate(sub);
    }

}
