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

import com.github.yingzhuo.commons.collections.collection.PredicatedCollection;
import com.github.yingzhuo.commons.collections.iterator.AbstractListIteratorDecorator;
import com.github.yingzhuo.commons.functor.Predicate;

/**
 * Decorates another <code>List</code> to validate that all additions
 * match a specified predicate.
 * <p>
 * This list exists to provide validation for the decorated list.
 * It is normally created to decorate an empty list.
 * If an object cannot be added to the list, an IllegalArgumentException is thrown.
 * <p>
 * One usage would be to ensure that no null entries are added to the list.
 * <pre>List list = PredicatedList.decorate(new ArrayList(), NotNullPredicate.INSTANCE);</pre>
 * <p>
 */
public class PredicatedList<E> extends PredicatedCollection<E> implements List<E> {

    /** Serialization version */
    private static final long serialVersionUID = -5722039223898659102L;

    /**
     * Factory method to create a predicated (validating) list.
     * <p>
     * If there are any elements already in the list being decorated, they
     * are validated.
     * 
     * @param list  the list to decorate, must not be null
     * @param predicate  the predicate to use for validation, must not be null
     * @throws IllegalArgumentException if list or predicate is null
     * @throws IllegalArgumentException if the list contains invalid elements
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E> List<E> decorate(List<E> list, Predicate<E> predicate) {
        return new PredicatedList(list, predicate);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * <p>
     * If there are any elements already in the list being decorated, they
     * are validated.
     * 
     * @param list  the list to decorate, must not be null
     * @param predicate  the predicate to use for validation, must not be null
     * @throws IllegalArgumentException if list or predicate is null
     * @throws IllegalArgumentException if the list contains invalid elements
     */
    protected PredicatedList(List<E> list, Predicate<E> predicate) {
        super(list, predicate);
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
    public E get(int index) {
        return getList().get(index);
    }

    public int indexOf(Object object) {
        return getList().indexOf(object);
    }

    public int lastIndexOf(Object object) {
        return getList().lastIndexOf(object);
    }

    public E remove(int index) {
        return getList().remove(index);
    }

    //-----------------------------------------------------------------------
    public void add(int index, E object) {
        validate(object);
        getList().add(index, object);
    }

    public boolean addAll(int index, Collection<? extends E> coll) {
        for (Iterator<? extends E> it = coll.iterator(); it.hasNext(); ) {
            validate(it.next());
        }
        return getList().addAll(index, coll);
    }

    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public ListIterator<E> listIterator(int i) {
        return new PredicatedListIterator(getList().listIterator(i), this.predicate);
    }

    public E set(int index, E object) {
        validate(object);
        return getList().set(index, object);
    }

    public List<E> subList(int fromIndex, int toIndex) {
        List<E> sub = getList().subList(fromIndex, toIndex);
        return PredicatedList.decorate(sub, predicate);
    }

    /**
     * Inner class Iterator for the PredicatedList
     */
    protected static class PredicatedListIterator<T> extends AbstractListIteratorDecorator<T> {
        
    	private Predicate<T> predicate;
    	
        protected PredicatedListIterator(ListIterator<T> iterator, Predicate<T> predicate) {
            super(iterator);
            this.predicate = predicate;
        }
        
        public void add(T object) {
        	_validate(object);
            iterator.add(object);
        }
        
        public void set(T object) {
        	_validate(object);
            iterator.set(object);
        }
        
        private void _validate(T object) {
        	if (predicate.evaluate(object) == false) {
                throw new IllegalArgumentException("Cannot add Object '" + object + "' - Predicate rejected it");
            }
        }
    }

}