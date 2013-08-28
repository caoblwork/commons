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


/**
 * Closure implementation acts as an if statement calling one or other closure
 * based on a predicate.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class IfClosure<T> implements Closure<T>, Serializable {

    /** Serial version UID */
    private static final long serialVersionUID = 3518477308466486130L;

    private final Predicate<T> iPredicate;
    private final Closure<T> iTrueClosure;
    private final Closure<T> iFalseClosure;

    /**
     * Factory method that performs validation.
     * <p>
     * This factory creates a closure that performs no action when
     * the predicate is false.
     * 
     * @param predicate  predicate to switch on
     * @param trueClosure  closure used if true
     * @return the <code>if</code> closure
     * @throws IllegalArgumentException if either argument is null
     */
    public static <T> Closure<T> getInstance(Predicate<T> predicate, Closure<T> trueClosure) {
        return getInstance(predicate, trueClosure, NOPClosure.INSTANCE);
    }

    /**
     * Factory method that performs validation.
     * 
     * @param predicate  predicate to switch on
     * @param trueClosure  closure used if true
     * @param falseClosure  closure used if false
     * @return the <code>if</code> closure
     * @throws IllegalArgumentException if any argument is null
     */
	public static <T> Closure<T> getInstance(Predicate<T> predicate, Closure<T> trueClosure, Closure<T> falseClosure) {
        Validate.notNull(predicate);
        Validate.notNull(trueClosure);
        Validate.notNull(falseClosure);
        return new IfClosure(predicate, trueClosure, falseClosure);
    }

    /**
     * Constructor that performs no validation.
     * Use <code>getInstance</code> if you want that.
     * <p>
     * This constructor creates a closure that performs no action when
     * the predicate is false.
     * 
     * @param predicate  predicate to switch on, not null
     * @param trueClosure  closure used if true, not null
     */
    private IfClosure(Predicate<T> predicate, Closure<T> trueClosure) {
        this(predicate, trueClosure, NOPClosure.INSTANCE);
    }

    /**
     * Constructor that performs no validation.
     * Use <code>getInstance</code> if you want that.
     * 
     * @param predicate  predicate to switch on, not null
     * @param trueClosure  closure used if true, not null
     * @param falseClosure  closure used if false, not null
     */
    private IfClosure(Predicate<T> predicate, Closure<T> trueClosure, Closure<T> falseClosure) {
        super();
        iPredicate = predicate;
        iTrueClosure = trueClosure;
        iFalseClosure = falseClosure;
    }

    /**
     * Executes the true or false closure accoring to the result of the predicate.
     * 
     * @param input  the input object
     */
    public void execute(T input) {
        if (iPredicate.evaluate(input) == true) {
            iTrueClosure.execute(input);
        } else {
            iFalseClosure.execute(input);
        }
    }

    /**
     * Gets the predicate.
     * 
     * @return the predicate
     */
    public Predicate<T> getPredicate() {
        return iPredicate;
    }

    /**
     * Gets the closure called when true.
     * 
     * @return the closure
     */
    public Closure<T> getTrueClosure() {
        return iTrueClosure;
    }

    /**
     * Gets the closure called when false.
     * 
     * @return the closure
     */
    public Closure<T> getFalseClosure() {
        return iFalseClosure;
    }

}