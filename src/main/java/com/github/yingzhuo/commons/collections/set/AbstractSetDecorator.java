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

import com.github.yingzhuo.commons.collections.collection.AbstractCollectionDecorator;

/**
 * Decorates another <code>Set</code> to provide additional behaviour.
 * <p>
 * Methods are forwarded directly to the decorated set. </p>
 *
 */
public abstract class AbstractSetDecorator<E> extends AbstractCollectionDecorator<E> implements Set<E> {

    /**
     * Constructor only used in deserialization, do not use otherwise.
     */
    protected AbstractSetDecorator() {
        super();
    }

    /**
     * Constructor that wraps (not copies).
     * 
     * @param set  the set to decorate, must not be null
     * @throws IllegalArgumentException if set is null
     */
    protected AbstractSetDecorator(Set<E> set) {
        super(set);
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