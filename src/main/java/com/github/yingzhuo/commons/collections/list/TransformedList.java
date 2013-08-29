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
import java.util.List;
import java.util.ListIterator;

import com.github.yingzhuo.commons.collections.collection.TransformedCollection;
import com.github.yingzhuo.commons.collections.iterator.AbstractListIteratorDecorator;
import com.github.yingzhuo.commons.functor.Transformer;

/**
 * Decorates another <code>List</code> to transform objects that are added.
 * <p>
 * The add and set methods are affected by this class.
 * Thus objects must be removed or searched for using their transformed form.
 * For example, if the transformation converts Strings to Integers, you must
 * use the Integer form to remove objects.
 * <p>
 */
public class TransformedList<E> extends TransformedCollection<E> implements List<E> {

    /** Serialization version */
    private static final long serialVersionUID = 1077193035000013141L;

    /**
     * Factory method to create a transforming list.
     * <p>
     * If there are any elements already in the list being decorated, they
     * are NOT transformed.
     * 
     * @param list  the list to decorate, must not be null
     * @param transformer  the transformer to use for conversion, must not be null
     * @throws IllegalArgumentException if list or transformer is null
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E> List<E> decorate(List<E> list, Transformer<E> transformer) {
        return new TransformedList(list, transformer);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * <p>
     * If there are any elements already in the list being decorated, they
     * are NOT transformed.
     * 
     * @param list  the list to decorate, must not be null
     * @param transformer  the transformer to use for conversion, must not be null
     * @throws IllegalArgumentException if list or transformer is null
     */
    protected TransformedList(List<E> list, Transformer<E> transformer) {
        super(list, transformer);
    }

    /**
     * Gets the decorated list.
     * 
     * @return the decorated list
     */
    protected List<E> getList() {
        return (List<E>) collection;
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
        object = transform(object);
        getList().add(index, object);
    }

    public boolean addAll(int index, Collection<? extends E> coll) {
        coll = transform(coll);
        return getList().addAll(index, coll);
    }

    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ListIterator<E> listIterator(int i) {
        return new TransformedListIterator(getList().listIterator(i), transformer);
    }

    public E set(int index, E object) {
        object = transform(object);
        return getList().set(index, object);
    }

	public List<E> subList(int fromIndex, int toIndex) {
        List<E> sub = getList().subList(fromIndex, toIndex);
        return TransformedList.decorate(sub, transformer);
    }

    /**
     * Inner class Iterator for the TransformedList
     */
    private static class TransformedListIterator<T> extends AbstractListIteratorDecorator<T> {
        
    	private Transformer<T> transformer;
    	
        protected TransformedListIterator(ListIterator<T> iterator, Transformer<T> transformer) {
            super(iterator);
            this.transformer = transformer;
        }
        
        public void add(T object) {
            object = transformer.transform(object);
            iterator.add(object);
        }
        
        public void set(T object) {
            object = transformer.transform(object);
            iterator.set(object);
        }
    }

}
