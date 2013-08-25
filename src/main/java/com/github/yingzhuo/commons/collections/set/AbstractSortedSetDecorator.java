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
import java.util.Set;
import java.util.SortedSet;

/**
 * Decorates another <code>SortedSet</code> to provide additional behaviour.
 *
 */
public abstract class AbstractSortedSetDecorator<E> extends AbstractSetDecorator<E> implements SortedSet<E> {

    /**
     * Constructor only used in deserialization, do not use otherwise.
     */
    protected AbstractSortedSetDecorator() {
        super();
    }

    /**
     * Constructor that wraps (not copies).
     * 
     * @param set  the set to decorate, must not be null
     * @throws IllegalArgumentException if set is null
     */
    protected AbstractSortedSetDecorator(Set<E> set) {
        super(set);
    }

    /**
     * Gets the sorted set being decorated.
     * 
     * @return the decorated set
     */
    protected SortedSet<E> getSortedSet() {
        return (SortedSet<E>) getCollection();
    }
    
    //-----------------------------------------------------------------------
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return getSortedSet().subSet(fromElement, toElement);
    }

    public SortedSet<E> headSet(E toElement) {
        return getSortedSet().headSet(toElement);
    }

    public SortedSet<E> tailSet(E fromElement) {
        return getSortedSet().tailSet(fromElement);
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
