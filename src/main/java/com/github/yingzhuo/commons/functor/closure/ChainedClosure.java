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
import com.github.yingzhuo.commons.lang.Validate;

/**
 * Closure implementation that chains the specified closures together.
 * 
 * @author Stephen Colebourne
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ChainedClosure<T> implements Closure<T>, Serializable {

    /** Serial version UID */
    private static final long serialVersionUID = -3520677225766901240L;

    /** The closures to call in turn */
    private final Closure<T>[] iClosures;

    /**
     * Factory method that performs validation.
     * 
     * @param closures  the closures, not null and no null element
     * @return the <code>chained</code> closure
     * @throws IllegalArgumentException if either closure is null
     * 
     */
    public static <T> Closure<T> getInstance(Closure<T>... closures) {
    	Validate.notNull(closures);
    	Validate.noNullElements(closures);
    	return new ChainedClosure(closures);
    }

    /**
     * Factory method that performs validation.
     * 
     * @param closure1  the first closure, not null
     * @param closure2  the second closure, not null
     * @return the <code>chained</code> closure
     * @throws IllegalArgumentException if either closure is null
     */
	public static <T> Closure<T> getInstance(Closure<T> closure1, Closure<T> closure2) {
        if (closure1 == null || closure2 == null) {
            throw new IllegalArgumentException("Closures must not be null");
        }
        Closure<T>[] closures = new Closure[] { closure1, closure2 };
        return new ChainedClosure(closures);
    }

    /**
     * Constructor that performs no validation.
     * Use <code>getInstance</code> if you want that.
     * 
     * @param closures  the closures to chain, not copied, no nulls
     */
    private ChainedClosure(Closure<T>[] closures) {
    	Validate.notNull(closures);
    	Validate.noNullElements(closures);
        iClosures = closures;
    }

    /**
     * Execute a list of closures.
     * 
     * @param input  the input object passed to each closure
     */
    public void execute(T input) {
        for (int i = 0; i < iClosures.length; i++) {
            iClosures[i].execute(input);
        }
    }

    /**
     * Gets the closures, do not modify the array.
     * @return the closures
     */
    public Closure<T>[] getClosures() {
        return iClosures;
    }

}
