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
package com.github.yingzhuo.commons.collections.set;

import java.util.Comparator;
import java.util.SortedSet;

import com.github.yingzhuo.commons.functor.Predicate;

/**
 * Decorates another <code>SortedSet</code> to validate that all additions
 * match a specified predicate.
 * <p>
 * This set exists to provide validation for the decorated set.
 * It is normally created to decorate an empty set.
 * If an object cannot be added to the set, an IllegalArgumentException is thrown.
 * <p>
 * One usage would be to ensure that no null entries are added to the set.
 * <pre>SortedSet set = PredicatedSortedSet.decorate(new TreeSet(), NotNullPredicate.INSTANCE);</pre>
 */
public class PredicatedSortedSet<E> extends PredicatedSet<E> implements SortedSet<E> {

    /** Serialization version */
    private static final long serialVersionUID = -9110948148132275052L;

    /**
     * Factory method to create a predicated (validating) sorted set.
     * <p>
     * If there are any elements already in the set being decorated, they
     * are validated.
     * 
     * @param set  the set to decorate, must not be null
     * @param predicate  the predicate to use for validation, must not be null
     * @throws IllegalArgumentException if set or predicate is null
     * @throws IllegalArgumentException if the set contains invalid elements
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <E> SortedSet<E> decorate(SortedSet<E> set, Predicate<E> predicate) {
        return new PredicatedSortedSet(set, predicate);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * <p>
     * If there are any elements already in the set being decorated, they
     * are validated.
     * 
     * @param set  the set to decorate, must not be null
     * @param predicate  the predicate to use for validation, must not be null
     * @throws IllegalArgumentException if set or predicate is null
     * @throws IllegalArgumentException if the set contains invalid elements
     */
    protected PredicatedSortedSet(SortedSet<E> set, Predicate<E> predicate) {
        super(set, predicate);
    }

    /**
     * Gets the sorted set being decorated.
     * 
     * @return the decorated sorted set
     */
    private SortedSet<E> getSortedSet() {
        return (SortedSet<E>) getCollection();
    }

    //-----------------------------------------------------------------------
    public SortedSet<E> subSet(E fromElement, E toElement) {
        SortedSet<E> sub = getSortedSet().subSet(fromElement, toElement);
        return PredicatedSortedSet.decorate(sub, predicate);
    }

    public SortedSet<E> headSet(E toElement) {
        SortedSet<E> sub = getSortedSet().headSet(toElement);
        return PredicatedSortedSet.decorate(sub, predicate);
    }

    public SortedSet<E> tailSet(E fromElement) {
        SortedSet<E> sub = getSortedSet().tailSet(fromElement);
        return PredicatedSortedSet.decorate(sub, predicate);
    }

    public E first() {
        return getSortedSet().first();
    }

    public E last() {
        return getSortedSet().last();
    }

    public Comparator<? super E> comparator() {
        return getSortedSet().comparator();
    }

}
