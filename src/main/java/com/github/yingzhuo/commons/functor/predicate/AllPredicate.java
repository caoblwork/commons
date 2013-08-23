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
 * Predicate implementation that returns true if all the
 * predicates return true.
 *
 * @author Stephen Colebourne
 * @author Matt Benson
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public final class AllPredicate<T> implements Predicate<T>, PredicateDecorator<T>, Serializable {

    /** Serial version UID */
    private static final long serialVersionUID = -3094696765038308799L;
    
    /** The array of predicates to call */
    private final Predicate<T>[] iPredicates;

    /**
     * Factory to create the predicate.
     *
     * @param predicates  the predicates to check, cloned, not null
     * @return the <code>all</code> predicate
     * @throws IllegalArgumentException if the predicates array is null
     * @throws IllegalArgumentException if any predicate in the array is null
     */
    
	public static <T> Predicate<T> getInstance(Predicate<T>... predicates) {
        return new AllPredicate(predicates);
    }

    /**
     * Constructor that performs no validation.
     * Use <code>getInstance</code> if you want that.
     * 
     * @param predicates  the predicates to check, not cloned, not null
     */
    private AllPredicate(Predicate<T>... predicates) {
        Validate.noNullElements(predicates);
        iPredicates = predicates;
    }

    /**
     * Evaluates the predicate returning true if all predicates return true.
     * 
     * @param object  the input object
     * @return true if all decorated predicates return true
     */
    public boolean evaluate(T object) {
        for (int i = 0; i < iPredicates.length; i++) {
            if (iPredicates[i].evaluate(object) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the predicates, do not modify the array.
     * 
     * @return the predicates
     */
    public Predicate<T>[] getPredicates() {
        return iPredicates;
    }

}
