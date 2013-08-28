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
import com.github.yingzhuo.commons.collections.collection.AbstractCollectionDecorator;

/**
 * Decorates another <code>Bag</code> to provide additional behaviour.
 * <p>
 * Methods are forwarded directly to the decorated bag. </p>
 *
 */
public abstract class AbstractBagDecorator<E>
        extends AbstractCollectionDecorator<E> implements Bag<E> {

    /**
     * Constructor only used in deserialization, do not use otherwise.
     */
    protected AbstractBagDecorator() {
        super();
    }

    /**
     * Constructor that wraps (not copies).
     * 
     * @param bag  the bag to decorate, must not be null
     * @throws IllegalArgumentException if list is null
     */
    protected AbstractBagDecorator(Bag<E> bag) {
        super(bag);
    }

    /**
     * Gets the bag being decorated.
     * 
     * @return the decorated bag
     */
    protected Bag<E> getBag() {
        return (Bag<E>) getCollection();
    }

    //-----------------------------------------------------------------------
    public int getCount(E object) {
        return getBag().getCount(object);
    }

    public boolean add(E object, int count) {
        return getBag().add(object, count);
    }

    public boolean remove(E object, int count) {
        return getBag().remove(object, count);
    }

    public Set<E> uniqueSet() {
        return getBag().uniqueSet();
    }

}
