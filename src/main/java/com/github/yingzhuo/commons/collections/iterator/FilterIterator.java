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

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.github.yingzhuo.commons.functor.Predicate;
import com.github.yingzhuo.commons.functor.predicate.TruePredicate;
import com.github.yingzhuo.commons.lang.Validate;

/** 
 * Decorates another {@link Iterator} using a predicate to filter elements.
 * <p>
 * This iterator decorates the underlying iterator, only allowing through
 * those elements that match the specified {@link Predicate Predicate}.
 *
 */
public class FilterIterator<E> implements Iterator<E> {

    /** The iterator being used */
    private Iterator<E> iterator;
    /** The predicate being used */
    private Predicate<E> predicate;
    /** The next object in the iteration */
    private E nextObject;
    /** Whether the next object has been calculated yet */
    private boolean nextObjectSet = false;

    /**
     * Factory method to create an predicated iterator.
     * 
     * @param iterator  the iterator to use, not null
     * @param predicate  the predicate to use, null means <code>TruePredicate</code>
     */
    public static <E> Iterator<E> decorate(Iterator<E> iterator, Predicate<E> predicate) {
    	return new FilterIterator<E>(iterator, predicate);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructs a new <code>FilterIterator</code> that will use the
     * given iterator and predicate.
     *
     * @param iterator  the iterator to use, not null
     * @param predicate  the predicate to use, null means <code>TruePredicate</code>
     */
    @SuppressWarnings("unchecked")
	private FilterIterator(Iterator<E> iterator, Predicate<E> predicate) {
        Validate.notNull(iterator);
        this.iterator = iterator;
        this.predicate = predicate != null ? predicate : TruePredicate.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /** 
     * Returns true if the underlying iterator contains an object that 
     * matches the predicate.
     *
     * @return true if there is another object that matches the predicate
     * @throws NullPointerException if either the iterator or predicate are null
     */
    public boolean hasNext() {
        if (nextObjectSet) {
            return true;
        } else {
            return setNextObject();
        }
    }

    /** 
     * Returns the next object that matches the predicate.
     *
     * @return the next object which matches the given predicate
     * @throws NullPointerException if either the iterator or predicate are null
     * @throws NoSuchElementException if there are no more elements that
     *  match the predicate 
     */
    public E next() {
        if (!nextObjectSet) {
            if (!setNextObject()) {
                throw new NoSuchElementException();
            }
        }
        nextObjectSet = false;
        return nextObject;
    }

    /**
     * Removes from the underlying collection of the base iterator the last
     * element returned by this iterator.
     * This method can only be called
     * if <code>next()</code> was called, but not after
     * <code>hasNext()</code>, because the <code>hasNext()</code> call
     * changes the base iterator.
     *
     * @throws IllegalStateException if <code>hasNext()</code> has already
     *  been called.
     */
    public void remove() {
        if (nextObjectSet) {
            throw new IllegalStateException("remove() cannot be called");
        }
        iterator.remove();
    }

    //-----------------------------------------------------------------------
    /** 
     * Gets the iterator this iterator is using.
     *
     * @return the iterator
     */
    public Iterator<E> getIterator() {
        return iterator;
    }

    //-----------------------------------------------------------------------
    /** 
     * Gets the predicate this iterator is using.
     *
     * @return the predicate
     */
    public Predicate<E> getPredicate() {
        return predicate;
    }

    //-----------------------------------------------------------------------
    /**
     * Set nextObject to the next object. If there are no more 
     * objects then return false. Otherwise, return true.
     */
    private boolean setNextObject() {
        while (iterator.hasNext()) {
            E object = iterator.next();
            if (predicate.evaluate(object)) {
                nextObject = object;
                nextObjectSet = true;
                return true;
            }
        }
        return false;
    }

}
