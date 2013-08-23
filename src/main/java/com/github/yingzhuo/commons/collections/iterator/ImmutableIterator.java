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
package com.github.yingzhuo.commons.collections.iterator;

import java.util.Iterator;

import com.github.yingzhuo.commons.immutable.Immutable;
import com.github.yingzhuo.commons.lang.Validate;

/** 
 * Decorates an iterator such that it cannot be modified.
 *
 */
public final class ImmutableIterator<E> implements Iterator<E>, Immutable {

    /** The iterator being decorated */
    private Iterator<E> iterator;

    //-----------------------------------------------------------------------
    /**
     * Decorates the specified iterator such that it cannot be modified.
     * <p>
     * If the iterator is already unmodifiable it is returned directly.
     *
     * @param iterator  the iterator to decorate
     * @throws IllegalArgumentException if the iterator is null
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <E> Iterator<E> decorate(Iterator<E> iterator) {
        Validate.notNull(iterator);
        if (iterator instanceof Immutable) {
            return iterator;
        }
        return new ImmutableIterator(iterator);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Constructor.
     *
     * @param iterator  the iterator to decorate
     */
    private ImmutableIterator(Iterator<E> iterator) {
        this.iterator = (Iterator<E>) iterator;
    }

    //-----------------------------------------------------------------------
    public boolean hasNext() {
        return iterator.hasNext();
    }

    public E next() {
        return iterator.next();
    }

    public void remove() {
        throw new UnsupportedOperationException("remove() is not supported");
    }

}
