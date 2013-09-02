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
package com.github.yingzhuo.commons.collections.map;

import java.util.Comparator;
import java.util.SortedMap;

import com.github.yingzhuo.commons.functor.Transformer;

/**
 * Decorates another <code>SortedMap </code> to transform objects that are added.
 * <p>
 * The Map put methods and Map.Entry setValue method are affected by this class.
 * Thus objects must be removed or searched for using their transformed form.
 * For example, if the transformation converts Strings to Integers, you must
 * use the Integer form to remove objects.
 * <p>
 * <strong>Note that TransformedSortedMap is not synchronized and is not thread-safe.</strong>
 * If you wish to use this map from multiple threads concurrently, you must use
 * appropriate synchronization. The simplest approach is to wrap this map
 * using {@link java.util.Collections#synchronizedSortedMap}. This class may throw 
 * exceptions when accessed by concurrent threads without synchronization.
 * <p>
 */
public class TransformedSortedMap<K, V>
        extends TransformedMap<K, V>
        implements SortedMap<K, V>
{

    /** Serialization version */
    private static final long serialVersionUID = -8751771676410385778L;
    
    /**
     * Factory method to create a transforming sorted map.
     * <p>
     * If there are any elements already in the map being decorated, they
     * are NOT transformed.
     * Constrast this with {@link #decorateTransform}.
     * 
     * @param map  the map to decorate, must not be null
     * @param keyTransformer  the predicate to validate the keys, null means no transformation
     * @param valueTransformer  the predicate to validate to values, null means no transformation
     * @throws IllegalArgumentException if the map is null
     */
    public static <K, V> SortedMap<K, V> decorate(SortedMap<K, V> map, Transformer<K> keyTransformer, Transformer<V> valueTransformer) {
        return new TransformedSortedMap<K, V>(map, keyTransformer, valueTransformer);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * <p>
     * If there are any elements already in the collection being decorated, they
     * are NOT transformed.</p>
     * 
     * @param map  the map to decorate, must not be null
     * @param keyTransformer  the predicate to validate the keys, null means no transformation
     * @param valueTransformer  the predicate to validate to values, null means no transformation
     * @throws IllegalArgumentException if the map is null
     */
    protected TransformedSortedMap(SortedMap<K, V> map, Transformer<K> keyTransformer, Transformer<V> valueTransformer) {
        super(map, keyTransformer, valueTransformer);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the map being decorated.
     * 
     * @return the decorated map
     */
    protected SortedMap<K, V> getSortedMap() {
        return (SortedMap<K, V>) map;
    }

    //-----------------------------------------------------------------------
    public K firstKey() {
        return getSortedMap().firstKey();
    }

    public K lastKey() {
        return getSortedMap().lastKey();
    }

    public Comparator<? super K> comparator() {
        return getSortedMap().comparator();
    }

    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        SortedMap<K, V> map = getSortedMap().subMap(fromKey, toKey);
        return new TransformedSortedMap<K, V>(map, keyTransformer, valueTransformer);
    }

    public SortedMap<K, V> headMap(K toKey) {
        SortedMap<K, V> map = getSortedMap().headMap(toKey);
        return new TransformedSortedMap<K, V>(map, keyTransformer, valueTransformer);
    }

    public SortedMap<K, V> tailMap(K fromKey) {
        SortedMap<K, V> map = getSortedMap().tailMap(fromKey);
        return new TransformedSortedMap<K, V>(map, keyTransformer, valueTransformer);
    }

}
