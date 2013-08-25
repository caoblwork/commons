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
package com.github.yingzhuo.commons.functor.predicate;

import java.io.Serializable;

import com.github.yingzhuo.commons.functor.Predicate;
import com.github.yingzhuo.commons.lang.Validate;

/**
 * Predicate implementation that returns the opposite of the decorated predicate.
 * 
 * @author Stephen Colebourne
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class NotPredicate<T> implements Predicate<T>, PredicateDecorator<T>, Serializable {

    /** Serial version UID */
    private static final long serialVersionUID = -2654603322338049674L;
    
    private final Predicate<T> iPredicate;
    
    /**
     * Factory to create the not predicate.
     * 
     * @param predicate  the predicate to decorate, not null
     * @return the predicate
     * @throws IllegalArgumentException if the predicate is null
     */
	public static <T> Predicate<T> getInstance(Predicate<T> predicate) {
        return new NotPredicate(predicate);
    }

    /**
     * Constructor that performs no validation.
     * Use <code>getInstance</code> if you want that.
     * 
     * @param predicate  the predicate to call after the null check
     */
    private NotPredicate(Predicate<T> predicate) {
        Validate.notNull(predicate);
        iPredicate = predicate;
    }

    /**
     * Evaluates the predicate returning the opposite to the stored predicate.
     * 
     * @param object  the input object
     * @return true if predicate returns false
     */
    public boolean evaluate(T object) {
        return !(iPredicate.evaluate(object));
    }

    /**
     * Gets the predicate being decorated.
     * 
     * @return the predicate as the only element in an array
     * @since Commons Collections 3.1
     */
    public Predicate[] getPredicates() {
        return new Predicate[] {iPredicate};
    }

}