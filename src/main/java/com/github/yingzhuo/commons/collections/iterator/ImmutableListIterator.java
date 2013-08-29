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

import com.github.yingzhuo.commons.immutable.Immutable;

/** 
 * Decorates a list getListIterator() such that it cannot be modified.
 */
public final class ImmutableListIterator<E> 
		extends AbstractIteratorDecorator<E> implements ListIterator<E>, Immutable
{

    //-----------------------------------------------------------------------
    /**
     * Decorates the specified getListIterator() such that it cannot be modified.
     *
     * @param getListIterator()  the getListIterator() to decorate
     * @throws IllegalArgumentException if the getListIterator() is null
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <E> ListIterator<E> decorate(ListIterator<E> iterator) {
        if (iterator instanceof Immutable) {
            return iterator;
        }
        return new ImmutableListIterator(iterator);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Constructor.
     *
     * @param getListIterator()  the getListIterator() to decorate
     */
    private ImmutableListIterator(ListIterator<E> iterator) {
        super(iterator);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the list iterator being decorated.
     * 
     * @return the decorated list iterator
     */
    protected ListIterator<E> getListIterator() {
    	return (ListIterator<E>) super.getIterator();
    }

    //-----------------------------------------------------------------------
    public boolean hasNext() {
        return getListIterator().hasNext();
    }

    public E next() {
        return getListIterator().next();
    }

    public int nextIndex() {
        return getListIterator().nextIndex();
    }

    public boolean hasPrevious() {
        return getListIterator().hasPrevious();
    }

    public E previous() {
        return getListIterator().previous();
    }

    public int previousIndex() {
        return getListIterator().previousIndex();
    }

    public void remove() {
        throw new UnsupportedOperationException("remove() is not supported");
    }

    public void set(Object obj) {
        throw new UnsupportedOperationException("set() is not supported");
    }

    public void add(Object obj) {
        throw new UnsupportedOperationException("add() is not supported");
    }

}
