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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.github.yingzhuo.commons.collections.collection.ImmutableCollection;
import com.github.yingzhuo.commons.collections.set.ImmutableSet;
import com.github.yingzhuo.commons.immutable.Immutable;

/**
 * Decorates another <code>SortedMap</code> to ensure it can't be altered.
 */
public final class ImmutableSortedMap<K, V>
        extends AbstractSortedMapDecorator<K, V>
        implements Immutable, Serializable {

    /** Serialization version */
    private static final long serialVersionUID = 5805344239827376360L;

    /**
     * Factory method to create an unmodifiable sorted map.
     * 
     * @param map  the map to decorate, must not be null
     * @throws IllegalArgumentException if map is null
     */
    public static <K, V> SortedMap<K, V> decorate(SortedMap<K, V> map) {
        if (map instanceof Immutable) {
            return map;
        }
        return new ImmutableSortedMap<K, V>(map);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * 
     * @param map  the map to decorate, must not be null
     * @throws IllegalArgumentException if map is null
     */
    private ImmutableSortedMap(SortedMap<K, V> map) {
        super(map);
    }

    //-----------------------------------------------------------------------
    /**
     * Write the map out using a custom routine.
     * 
     * @param out  the output stream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(map);
    }

    /**
     * Read the map in using a custom routine.
     * 
     * @param in  the input stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        map = (Map) in.readObject();
    }

    //-----------------------------------------------------------------------
    public void clear() {
        throw new UnsupportedOperationException();
    }

    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public void putAll(Map<? extends K, ? extends V> mapToCopy) {
        throw new UnsupportedOperationException();
    }

    public V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = super.entrySet();
        return ImmutableEntrySet.decorate(set);
    }

    public Set<K> keySet() {
        Set<K> set = super.keySet();
        return ImmutableSet.decorate(set);
    }

    public Collection<V> values() {
        Collection<V> coll = super.values();
        return ImmutableCollection.decorate(coll);
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
        return ImmutableSortedMap.decorate(map);
    }

    public SortedMap<K, V> headMap(K toKey) {
        SortedMap<K, V> map = getSortedMap().headMap(toKey);
        return ImmutableSortedMap.decorate(map);
    }

    public SortedMap<K, V> tailMap(K fromKey) {
        SortedMap<K, V> map = getSortedMap().tailMap(fromKey);
        return ImmutableSortedMap.decorate(map);
    }

}
