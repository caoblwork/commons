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


/**
 * Decorates another <code>Set</code> to ensure it can't be altered.
 * <p>
 * This class is Serializable from Commons Collections 3.1.
 *
 */
//public final class ImmutableSet<E>
////        extends AbstractSerializableSetDecorator
//        implements Immutable, Serializable {
//
//    /** Serialization version */
//    private static final long serialVersionUID = 6499119872185240161L;
//
//    /**
//     * Factory method to create an unmodifiable set.
//     * 
//     * @param set  the set to decorate, must not be null
//     * @throws IllegalArgumentException if set is null
//     */
//    public static <E> Set<E> decorate(Set<E> set) {
//        if (set instanceof Immutable) {
//            return set;
//        }
//        return new ImmutableSet(set);
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Constructor that wraps (not copies).
//     * 
//     * @param set  the set to decorate, must not be null
//     * @throws IllegalArgumentException if set is null
//     */
//    private ImmutableSet(Set<E> set) {
//        super(set);
//    }
//
//    //-----------------------------------------------------------------------
//    public Iterator iterator() {
//        return UnmodifiableIterator.decorate(getCollection().iterator());
//    }
//
//    public boolean add(Object object) {
//        throw new UnsupportedOperationException();
//    }
//
//    public boolean addAll(Collection coll) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void clear() {
//        throw new UnsupportedOperationException();
//    }
//
//    public boolean remove(Object object) {
//        throw new UnsupportedOperationException();
//    }
//
//    public boolean removeAll(Collection coll) {
//        throw new UnsupportedOperationException();
//    }
//
//    public boolean retainAll(Collection coll) {
//        throw new UnsupportedOperationException();
//    }
//
//}
