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
package com.github.yingzhuo.commons.functor.closure;

import java.io.Serializable;

import com.github.yingzhuo.commons.functor.Closure;
import com.github.yingzhuo.commons.functor.Predicate;
import com.github.yingzhuo.commons.lang.Validate;
import com.github.yingzhuo.commons.lang.tuple.Pair;

/**
 * Closure implementation calls the closure whose predicate returns true,
 * like a switch statement.
 * 
 * @author Stephen Colebourne
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SwitchClosure<T> implements Closure<T>, Serializable {

    /** Serial version UID */
    private static final long serialVersionUID = 3518477308466486130L;

    /** The tests to consider */
    private final Predicate<T>[] iPredicates;
    private final Closure<T>[] iClosures;
    private final Closure<T> iDefault;

    /**
     * Create a new Closure that calls one of the closures depending 
     * on the predicates. 
     * <p>
     * The Map consists of Predicate keys and Closure values. A closure 
     * is called if its matching predicate returns true. Each predicate is evaluated
     * until one returns true. If no predicates evaluate to true, the default
     * closure is called. The default closure is set in the map with a 
     * null key. The ordering is that of the iterator() method on the entryset 
     * collection of the map.
     * 
     * @param predicatesAndClosures  a map of predicates to closures
     * @return the <code>switch</code> closure
     * @throws IllegalArgumentException if the map is null
     * @throws IllegalArgumentException if any closure in the map is null
     * @throws ClassCastException  if the map elements are of the wrong type
     */
	public static <T> Closure<T> getInstance(Closure<T> defaultClosure, Pair<Predicate<T>, Closure<T>>...pairs) {
        return new SwitchClosure(defaultClosure, pairs);
    }
    
    /**
     * Constructor that performs no validation.
     * Use <code>getInstance</code> if you want that.
     * 
     * @param defaultClosure  the closure to use if no match, null means nop
     * @param predicates  array of predicates, not cloned, no nulls
     * @param closures  matching array of closures, not cloned, no nulls
     */
	private SwitchClosure(Closure<T> defaultClosure, Pair<Predicate<T>, Closure<T>>...pairs) {
    	
    	if (defaultClosure == null) {
    		defaultClosure = NOPClosure.INSTANCE;
    	}
    	
    	Validate.notNull(pairs);
    	Validate.noNullElements(pairs);
    	
    	this.iDefault = defaultClosure;

    	int length  = pairs.length;
    	iPredicates = new Predicate[length];
    	iClosures   = new Closure[length];
    	
    	for (int i = 0; i < pairs.length; i ++) {
    		iPredicates[i] = pairs[i].getKey();
    		iClosures[i]   = pairs[i].getValue();
    	}
    	
    	Validate.noNullElements(iPredicates);
    	Validate.noNullElements(iClosures);
    }

    /**
     * Executes the closure whose matching predicate returns true
     * 
     * @param input  the input object
     */
    public void execute(T input) {
        for (int i = 0; i < iPredicates.length; i++) {
            if (iPredicates[i].evaluate(input) == true) {
                iClosures[i].execute(input);
                return;
            }
        }
        iDefault.execute(input);
    }

    /**
     * Gets the predicates, do not modify the array.
     * 
     * @return the predicates
     */
    public Predicate<T>[] getPredicates() {
        return iPredicates;
    }

    /**
     * Gets the closures, do not modify the array.
     * 
     * @return the closures
     */
    public Closure<T>[] getClosures() {
        return iClosures;
    }

    /**
     * Gets the default closure.
     * 
     * @return the default closure
     */
    public Closure<T> getDefaultClosure() {
        return iDefault;
    }
    
}
