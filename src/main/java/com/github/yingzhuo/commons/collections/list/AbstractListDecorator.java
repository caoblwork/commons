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

import com.github.yingzhuo.commons.collections.collection.AbstractCollectionDecorator;

/**
 * Decorates another <code>List</code> to provide additional behaviour.
 * <p>
 * Methods are forwarded directly to the decorated list.
 *
 */
public abstract class AbstractListDecorator<E> extends AbstractCollectionDecorator<E> implements List<E> {

    /**
     * Constructor only used in deserialization, do not use otherwise.
     * @since Commons Collections 3.1
     */
    protected AbstractListDecorator() {
        super();
    }

    /**
     * Constructor that wraps (not copies).
     * 
     * @param list  the list to decorate, must not be null
     * @throws IllegalArgumentException if list is null
     */
    protected AbstractListDecorator(List<E> list) {
        super(list);
    }

    /**
     * Gets the list being decorated.
     * 
     * @return the decorated list
     */
    protected List<E> getList() {
        return (List<E>) getCollection();
    }

    //-----------------------------------------------------------------------
    public void add(int index, E object) {
        getList().add(index, object);
    }

    public boolean addAll(int index, Collection<? extends E> coll) {
        return getList().addAll(index, coll);
    }

    public E get(int index) {
        return getList().get(index);
    }

    public int indexOf(Object object) {
        return getList().indexOf(object);
    }

    public int lastIndexOf(Object object) {
        return getList().lastIndexOf(object);
    }

    public ListIterator<E> listIterator() {
        return getList().listIterator();
    }

    public ListIterator<E> listIterator(int index) {
        return getList().listIterator(index);
    }

    public E remove(int index) {
        return getList().remove(index);
    }

    public E set(int index, E object) {
        return getList().set(index, object);
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return getList().subList(fromIndex, toIndex);
    }

}
