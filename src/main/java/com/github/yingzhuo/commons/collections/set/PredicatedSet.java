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

import java.util.Set;

import com.github.yingzhuo.commons.collections.collection.PredicatedCollection;
import com.github.yingzhuo.commons.functor.Predicate;

/**
 * Decorates another <code>Set</code> to validate that all additions
 * match a specified predicate.
 * <p>
 * This set exists to provide validation for the decorated set.
 * It is normally created to decorate an empty set.
 * If an object cannot be added to the set, an IllegalArgumentException is thrown.
 * <p>
 * One usage would be to ensure that no null entries are added to the set.
 * <pre>Set set = PredicatedSet.decorate(new HashSet(), NotNullPredicate.INSTANCE);</pre>
 *
 */
public class PredicatedSet<E> extends PredicatedCollection<E> implements Set<E> {

    /** Serialization version */
    private static final long serialVersionUID = -684521469108685117L;

    /**
     * Factory method to create a predicated (validating) set.
     * <p>
     * If there are any elements already in the set being decorated, they
     * are validated.
     * 
     * @param set  the set to decorate, must not be null
     * @param predicate  the predicate to use for validation, must not be null
     * @throws IllegalArgumentException if set or predicate is null
     */
	public static <E> Set<E> decorate(Set<E> set, Predicate<E> predicate) {
        return new PredicatedSet<E>(set, predicate);
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
    protected PredicatedSet(Set<E> set, Predicate<E> predicate) {
        super(set, predicate);
    }

    /**
     * Gets the set being decorated.
     * 
     * @return the decorated set
     */
    protected Set<E> getSet() {
        return (Set<E>) getCollection();
    }

}
