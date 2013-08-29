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
package com.github.yingzhuo.commons.collections.iterator;

import java.util.ListIterator;

import com.github.yingzhuo.commons.lang.Validate;

/**
 * Provides basic behaviour for decorating a list iterator with extra functionality.
 * <p>
 * All methods are forwarded to the decorated list iterator.
 */
public class AbstractListIteratorDecorator<E> implements ListIterator<E> {

    /** The iterator being decorated */
    protected final ListIterator<E> iterator;

    //-----------------------------------------------------------------------
    /**
     * Constructor that decorates the specified iterator.
     *
     * @param iterator  the iterator to decorate, must not be null
     * @throws IllegalArgumentException if the collection is null
     */
    public AbstractListIteratorDecorator(ListIterator<E> iterator) {
        Validate.notNull(iterator);
        this.iterator = iterator;
    }

    /**
     * Gets the iterator being decorated.
     * 
     * @return the decorated iterator
     */
    protected ListIterator<E> getListIterator() {
        return iterator;
    }

    //-----------------------------------------------------------------------
    public boolean hasNext() {
        return iterator.hasNext();
    }

    public E next() {
        return iterator.next();
    }

    public int nextIndex() {
        return iterator.nextIndex();
    }

    public boolean hasPrevious() {
        return iterator.hasPrevious();
    }

    public E previous() {
        return iterator.previous();
    }

    public int previousIndex() {
        return iterator.previousIndex();
    }

    public void remove() {
        iterator.remove();
    }

    public void set(E obj) {
        iterator.set(obj);
    }

    public void add(E obj) {
        iterator.add(obj);
    }
    
}
