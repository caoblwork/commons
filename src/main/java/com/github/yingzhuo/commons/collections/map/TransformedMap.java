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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.github.yingzhuo.commons.functor.Transformer;

/**
 * Decorates another <code>Map</code> to transform objects that are added.
 * <p>
 * The Map put methods and Map.Entry setValue method are affected by this class.
 * Thus objects must be removed or searched for using their transformed form.
 * For example, if the transformation converts Strings to Integers, you must
 * use the Integer form to remove objects.
 * <p>
 * <strong>Note that TransformedMap is not synchronized and is not thread-safe.</strong>
 * If you wish to use this map from multiple threads concurrently, you must use
 * appropriate synchronization. The simplest approach is to wrap this map
 * using {@link java.util.Collections#synchronizedMap(Map)}. This class may throw 
 * exceptions when accessed by concurrent threads without synchronization.
 * <p>
 */
public class TransformedMap<K, V>
        extends AbstractMapDecorator<K, V>
        implements Serializable {

    /** Serialization version */
    private static final long serialVersionUID = 7023152376788900464L;

    /** The transformer to use for the key */
    protected final Transformer<K> keyTransformer;
    /** The transformer to use for the value */
    protected final Transformer<V> valueTransformer;

    /**
     * Factory method to create a transforming map.
     * <p>
     * If there are any elements already in the map being decorated, they
     * are NOT transformed.
     * Constrast this with {@link #decorateTransform}.
     * 
     * @param map  the map to decorate, must not be null
     * @param keyTransformer  the transformer to use for key conversion, null means no transformation
     * @param valueTransformer  the transformer to use for value conversion, null means no transformation
     * @throws IllegalArgumentException if map is null
     */
    public static <K, V> Map<K, V> decorate(Map<K, V> map, Transformer<K> keyTransformer, Transformer<V> valueTransformer) {
        return new TransformedMap<K, V>(map, keyTransformer, valueTransformer);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     * <p>
     * If there are any elements already in the collection being decorated, they
     * are NOT transformed.
     * 
     * @param map  the map to decorate, must not be null
     * @param keyTransformer  the transformer to use for key conversion, null means no conversion
     * @param valueTransformer  the transformer to use for value conversion, null means no conversion
     * @throws IllegalArgumentException if map is null
     */
    protected TransformedMap(Map<K, V> map, Transformer<K> keyTransformer, Transformer<V> valueTransformer) {
        super(map);
        this.keyTransformer = keyTransformer;
        this.valueTransformer = valueTransformer;
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
    @SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        map = (Map<K, V>) in.readObject();
    }

    //-----------------------------------------------------------------------
    /**
     * Transforms a key.
     * <p>
     * The transformer itself may throw an exception if necessary.
     * 
     * @param object  the object to transform
     * @throws the transformed object
     */
    protected K transformKey(K object) {
        if (keyTransformer == null) {
            return object;
        }
        return keyTransformer.transform(object);
    }

    /**
     * Transforms a value.
     * <p>
     * The transformer itself may throw an exception if necessary.
     * 
     * @param object  the object to transform
     * @throws the transformed object
     */
    protected V transformValue(V object) {
        if (valueTransformer == null) {
            return object;
        }
        return valueTransformer.transform(object);
    }

    /**
     * Transforms a map.
     * <p>
     * The transformer itself may throw an exception if necessary.
     * 
     * @param map  the map to transform
     * @throws the transformed object
     */
    protected Map<K, V> transformMap(Map<K, V> map) {
        if (map.isEmpty()) {
            return map;
        }
        Map<K, V> result = new HashMap<K, V>(map.size());
        for (Iterator<Map.Entry<K, V>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<K, V> entry = it.next();
            result.put(transformKey(entry.getKey()), transformValue(entry.getValue()));
        }
        return result;
    }

    /**
     * Override to transform the value when using <code>setValue</code>.
     * 
     * @param value  the value to transform
     * @return the transformed value
     * @since Commons Collections 3.1
     */
    protected V checkSetValue(V value) {
        return valueTransformer.transform(value);
    }

    /**
     * Override to only return true when there is a value transformer.
     * 
     * @return true if a value transformer is in use
     * @since Commons Collections 3.1
     */
    protected boolean isSetValueChecking() {
        return (valueTransformer != null);
    }

    //-----------------------------------------------------------------------
    public V put(K key, V value) {
        key = transformKey(key);
        value = transformValue(value);
        return getMap().put(key, value);
    }

    public void putAll(Map<? extends K, ? extends V> mapToCopy) {
    	Map<K, V> mid = new HashMap<K, V>(mapToCopy);
    	mid = transformMap(mid);
        getMap().putAll(mid);
    }

}
