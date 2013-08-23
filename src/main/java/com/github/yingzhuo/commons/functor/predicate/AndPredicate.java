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
 * Predicate implementation that returns true if both the predicates return true.
 * 
 * @author Stephen Colebourne
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public final class AndPredicate<T> implements Predicate<T>, PredicateDecorator<T>, Serializable {

    /** Serial version UID */
    private static final long serialVersionUID = 4189014213763186912L;
    
    private final Predicate<T> iPredicate1;
    private final Predicate<T> iPredicate2;
    
    /**
     * Factory to create the predicate.
     * 
     * @param predicate1  the first predicate to check, not null
     * @param predicate2  the second predicate to check, not null
     * @return the <code>and</code> predicate
     * @throws IllegalArgumentException if either predicate is null
     */
	public static <T> Predicate<T> getInstance(Predicate<T> predicate1, Predicate<T> predicate2) {
        return new AndPredicate(predicate1, predicate2);
    }

    /**
     * Constructor that performs no validation.
     * Use <code>getInstance</code> if you want that.
     * 
     * @param predicate1  the first predicate to check, not null
     * @param predicate2  the second predicate to check, not null
     */
    private AndPredicate(Predicate<T> predicate1, Predicate<T> predicate2) {
        Validate.notNull(predicate1);
        Validate.notNull(predicate2);
        iPredicate1 = predicate1;
        iPredicate2 = predicate2;
    }

    /**
     * Evaluates the predicate returning true if both predicates return true.
     * 
     * @param object  the input object
     * @return true if both decorated predicates return true
     */
    public boolean evaluate(T object) {
       return (iPredicate1.evaluate(object) && iPredicate2.evaluate(object));
    }

    /**
     * Gets the two predicates being decorated as an array.
     * 
     * @return the predicates
     */
    public Predicate<T>[] getPredicates() {
    	return new Predicate[] {iPredicate1, iPredicate2};
    }

}
