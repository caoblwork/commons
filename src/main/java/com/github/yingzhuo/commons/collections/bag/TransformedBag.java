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
package com.github.yingzhuo.commons.collections.bag;

import java.util.Set;

import com.github.yingzhuo.commons.collections.Bag;
import com.github.yingzhuo.commons.collections.collection.TransformedCollection;
import com.github.yingzhuo.commons.collections.set.TransformedSet;
import com.github.yingzhuo.commons.functor.Transformer;

/**
 * Decorates another <code>Bag</code> to transform objects that are added.
 * <p>
 * The add methods are affected by this class.
 * Thus objects must be removed or searched for using their transformed form.
 * For example, if the transformation converts Strings to Integers, you must
 * use the Integer form to remove objects.
 * <p>
 */
public class TransformedBag<E>
        extends TransformedCollection<E> implements Bag<E> {

    /** Serialization version */
    private static final long serialVersionUID = 5421170911299074185L;

    /**
     * Factory method to create a transforming bag.
     * <p>
     * If there are any elements already in the bag being decorated, they
     * are NOT transformed.
     * 
     * @param bag  the bag to decorate, must not be null
     * @param transformer  the transformer to use for conversion, must not be null
     * @return a new transformed Bag
     * @throws IllegalArgumentException if bag or transformer is null
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <E> Bag<E> decorate(Bag<E> bag, Transformer<E> transformer) {
        return new TransformedBag(bag, transformer);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * <p>
     * If there are any elements already in the bag being decorated, they
     * are NOT transformed.
     * 
     * @param bag  the bag to decorate, must not be null
     * @param transformer  the transformer to use for conversion, must not be null
     * @throws IllegalArgumentException if bag or transformer is null
     */
    protected TransformedBag(Bag<E> bag, Transformer<E> transformer) {
        super(bag, transformer);
    }

    /**
     * Gets the decorated bag.
     * 
     * @return the decorated bag
     */
    protected Bag<E> getBag() {
        return (Bag<E>) collection;
    }

    //-----------------------------------------------------------------------
    public int getCount(E object) {
        return getBag().getCount(object);
    }

    public boolean remove(E object, int nCopies) {
        return getBag().remove(object, nCopies);
    }

    //-----------------------------------------------------------------------
    public boolean add(E object, int nCopies) {
        object = transform(object);
        return getBag().add(object, nCopies);
    }

    public Set<E> uniqueSet() {
        Set<E> set = getBag().uniqueSet();
        return TransformedSet.decorate(set, transformer);
    }

}
