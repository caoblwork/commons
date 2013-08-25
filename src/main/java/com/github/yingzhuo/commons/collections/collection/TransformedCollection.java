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
package com.github.yingzhuo.commons.collections.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.github.yingzhuo.commons.functor.Transformer;

/**
 * Decorates another <code>Collection</code> to transform objects that are added.
 * <p>
 * The add methods are affected by this class.
 * Thus objects must be removed or searched for using their transformed form.
 * For example, if the transformation converts Strings to Integers, you must
 * use the Integer form to remove objects.
 */
public class TransformedCollection<E> extends AbstractSerializableCollectionDecorator<E> {

    /** Serialization version */
    private static final long serialVersionUID = 8692300188161871514L;

    /** The transformer to use */
    protected final Transformer<E> transformer;

    /**
     * Factory method to create a transforming collection.
     * <p>
     * If there are any elements already in the collection being decorated, they
     * are NOT transformed.
     * 
     * @param coll  the collection to decorate, must not be null
     * @param transformer  the transformer to use for conversion, must not be null
     * @return a new transformed collection
     * @throws IllegalArgumentException if collection or transformer is null
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <E> Collection<E> decorate(Collection<E> coll, Transformer<E> transformer) {
        return new TransformedCollection(coll, transformer);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * <p>
     * If there are any elements already in the collection being decorated, they
     * are NOT transformed.
     * 
     * @param coll  the collection to decorate, must not be null
     * @param transformer  the transformer to use for conversion, must not be null
     * @throws IllegalArgumentException if collection or transformer is null
     */
    protected TransformedCollection(Collection<E> coll, Transformer<E> transformer) {
        super(coll);
        if (transformer == null) {
            throw new IllegalArgumentException("Transformer must not be null");
        }
        this.transformer = transformer;
    }

    /**
     * Transforms an object.
     * <p>
     * The transformer itself may throw an exception if necessary.
     * 
     * @param object  the object to transform
     * @return a transformed object
     */
    protected E transform(E object) {
        return transformer.transform(object);
    }

    /**
     * Transforms a collection.
     * <p>
     * The transformer itself may throw an exception if necessary.
     * 
     * @param coll  the collection to transform
     * @return a transformed object
     */
    @SuppressWarnings("unchecked")
	protected Collection<? extends E> transform(Collection<? extends E> coll) {
    	Collection<Object> list = new ArrayList<Object>(coll.size());
    	for (Iterator<? extends E> it = coll.iterator(); it.hasNext(); ) {
    		E o = it.next();
    		list.add(transform(o));
    	}
    	return (Collection<? extends E>) list;
    }

    //-----------------------------------------------------------------------
    public boolean add(E object) {
        object = transform(object);
        return getCollection().add(object);
    }

    public boolean addAll(Collection<? extends E> coll) {
        coll = transform(coll);
        return getCollection().addAll(coll);
    }

}
