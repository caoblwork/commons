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
import java.util.NoSuchElementException;

import com.github.yingzhuo.commons.functor.Predicate;
import com.github.yingzhuo.commons.functor.predicate.TruePredicate;
import com.github.yingzhuo.commons.lang.Validate;

/** 
 * Decorates another {@link ListIterator} using a predicate to filter elements.
 * <p>
 * This iterator decorates the underlying iterator, only allowing through
 * those elements that match the specified {@link Predicate Predicate}.
 *
 */
public class FilterListIterator<E> implements ListIterator<E> {

    /** The iterator being used */
    private ListIterator<E> iterator;
    
    /** The predicate being used */
    private Predicate<E> predicate;

    /** 
     * The value of the next (matching) object, when 
     * {@link #nextObjectSet} is true. 
     */
    private E nextObject;

    /** 
     * The value of the previous (matching) object, when 
     * {@link #previousObjectSet} is true. 
     */
    private E previousObject;

    /** 
     * Whether or not the {@link #nextObject} has been set
     * (possibly to <code>null</code>). 
     */
    private boolean nextObjectSet = false;   


    /** 
     * Whether or not the {@link #previousObject} has been set
     * (possibly to <code>null</code>). 
     */
    private boolean previousObjectSet = false;   

    /** 
     * The index of the element that would be returned by {@link #next}.
     */
    private int nextIndex = 0;
    
    //-----------------------------------------------------------------------
    
    /**
     * Factory method to create an predicated iterator.
     * 
     * @param iterator  the iterator to use, not null
     * @param predicate  the predicate to use, null means <code>TruePredicate</code>
     */
    public static <E> ListIterator<E> decorate(ListIterator<E> iterator, Predicate<E> predicate) {
    	return new FilterListIterator<E>(iterator, predicate);
    }

    
    //-----------------------------------------------------------------------
    /**
     * Constructs a new <code>FilterListIterator</code>.
     *
     * @param iterator  the iterator to use, not null
     * @param predicate  the predicate to use, null means <code>TruePredicate</code>
     */
    @SuppressWarnings("unchecked")
	private FilterListIterator(ListIterator<E> iterator, Predicate<E> predicate) {
        Validate.notNull(iterator);
        this.iterator = iterator;
        this.predicate = predicate != null ? predicate : TruePredicate.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /** Not supported. */
    public void add(Object o) {
        throw new UnsupportedOperationException("FilterListIterator.add(Object) is not supported.");
    }

    public boolean hasNext() {
        if(nextObjectSet) {
            return true;
        } else {
            return setNextObject();
        }
    }

    public boolean hasPrevious() {
        if(previousObjectSet) {
            return true;
        } else {
            return setPreviousObject();
        }
    }

    public E next() {
        if(!nextObjectSet) {
            if(!setNextObject()) {
                throw new NoSuchElementException();
            }
        }
        nextIndex++;
        E temp = nextObject;
        clearNextObject();
        return temp;
    }

    public int nextIndex() {
        return nextIndex;
    }

    public E previous() {
        if(!previousObjectSet) {
            if(!setPreviousObject()) {
                throw new NoSuchElementException();
            }
        }
        nextIndex--;
        E temp = previousObject;
        clearPreviousObject();
        return temp;
    }

    public int previousIndex() {
        return (nextIndex-1);
    }

    /** Not supported. */
    public void remove() {
        throw new UnsupportedOperationException("FilterListIterator.remove() is not supported.");
    }

    /** Not supported. */
    public void set(Object o) {
        throw new UnsupportedOperationException("FilterListIterator.set(Object) is not supported.");
    }

    //-----------------------------------------------------------------------
    /** 
     * Gets the iterator this iterator is using.
     * 
     * @return the iterator.
     */
    public ListIterator<E> getListIterator() {
        return iterator;
    }

    //-----------------------------------------------------------------------
    /** 
     * Gets the predicate this iterator is using.
     * 
     * @return the predicate.
     */
    public Predicate<E> getPredicate() {
        return predicate;
    }

    //-----------------------------------------------------------------------
    private void clearNextObject() {
        nextObject = null;
        nextObjectSet = false;
    }

    private boolean setNextObject() {
        // if previousObjectSet,
        // then we've walked back one step in the 
        // underlying list (due to a hasPrevious() call)
        // so skip ahead one matching object
        if(previousObjectSet) {
            clearPreviousObject();
            if(!setNextObject()) {
                return false;
            } else {
                clearNextObject();
            }
        }

        while(iterator.hasNext()) {
            E object = iterator.next();
            if(predicate.evaluate(object)) {
                nextObject = object;
                nextObjectSet = true;
                return true;
            }
        }
        return false;
    }

    private void clearPreviousObject() {
        previousObject = null;
        previousObjectSet = false;
    }

    private boolean setPreviousObject() {
        // if nextObjectSet,
        // then we've walked back one step in the 
        // underlying list (due to a hasNext() call)
        // so skip ahead one matching object
        if(nextObjectSet) {
            clearNextObject();
            if(!setPreviousObject()) {
                return false;
            } else {
                clearPreviousObject();
            }
        }

        while(iterator.hasPrevious()) {
            E object = iterator.previous();
            if(predicate.evaluate(object)) {
                previousObject = object;
                previousObjectSet = true;
                return true;
            }
        }
        return false;
    }

}
