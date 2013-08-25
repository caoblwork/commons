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

import com.github.yingzhuo.commons.functor.Transformer;

/**
 * Decorates another <code>SortedSet</code> to transform objects that are added.
 * <p>
 * The add methods are affected by this class.
 * Thus objects must be removed or searched for using their transformed form.
 * For example, if the transformation converts Strings to Integers, you must
 * use the Integer form to remove objects.
 */
public class TransformedSortedSet<E> extends TransformedSet<E> implements SortedSet<E> {

    /** Serialization version */
    private static final long serialVersionUID = -1675486811351124386L;

    /**
     * Factory method to create a transforming sorted set.
     * <p>
     * If there are any elements already in the set being decorated, they
     * are NOT transformed.
     * 
     * @param set  the set to decorate, must not be null
     * @param transformer  the transformer to use for conversion, must not be null
     * @throws IllegalArgumentException if set or transformer is null
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <E> SortedSet<E> decorate(SortedSet<E> set, Transformer<E> transformer) {
        return new TransformedSortedSet(set, transformer);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * <p>
     * If there are any elements already in the set being decorated, they
     * are NOT transformed.
     * 
     * @param set  the set to decorate, must not be null
     * @param transformer  the transformer to use for conversion, must not be null
     * @throws IllegalArgumentException if set or transformer is null
     */
    protected TransformedSortedSet(SortedSet<E> set, Transformer<E> transformer) {
        super(set, transformer);
    }

    /**
     * Gets the decorated set.
     * 
     * @return the decorated set
     */
    protected SortedSet<E> getSortedSet() {
        return (SortedSet<E>) collection;
    }

    //-----------------------------------------------------------------------
    public E first() {
        return getSortedSet().first();
    }

    public E last() {
        return getSortedSet().last();
    }

    public Comparator<? super E> comparator() {
        return getSortedSet().comparator();
    }

    //-----------------------------------------------------------------------
    public SortedSet<E> subSet(E fromElement, E toElement) {
        SortedSet<E> set = getSortedSet().subSet(fromElement, toElement);
        return TransformedSortedSet.decorate(set, transformer);
    }

    public SortedSet<E> headSet(E toElement) {
        SortedSet<E> set = getSortedSet().headSet(toElement);
        return TransformedSortedSet.decorate(set, transformer);
    }

    public SortedSet<E> tailSet(E fromElement) {
        SortedSet<E> set = getSortedSet().tailSet(fromElement);
        return TransformedSortedSet.decorate(set, transformer);
    }

}