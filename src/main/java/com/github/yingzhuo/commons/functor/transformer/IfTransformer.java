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
package com.github.yingzhuo.commons.functor.transformer;

import java.io.Serializable;

import com.github.yingzhuo.commons.exception.FunctorException;
import com.github.yingzhuo.commons.functor.Predicate;
import com.github.yingzhuo.commons.functor.Transformer;
import com.github.yingzhuo.commons.lang.Validate;

/**
 * Closure implementation acts as an if statement calling one or other closure
 * based on a predicate.
 * 
 */
public class IfTransformer<T> implements Transformer<T>, Serializable {

	/** Serial version UID */
	private static final long serialVersionUID = 3518477308466486130L;

	/** The test */
	private final Predicate<T> iPredicate;
	/** The closure to use if true */
	private final Transformer<T> iTrueTransformer;
	/** The closure to use if false */
	private final Transformer<T> iFalseTransformer;
	
	/**
	 * Factory method that performs validation.
	 * 
	 * @param predicate predicate to switch on, not null
	 * @param trueTransformer transformer used if true, not null
	 * @return the <code>if</code> transformer
	 * @throws IllegalArgumentException if either argument is null
	 */
	@SuppressWarnings({ "unchecked"})
	public static <T> Transformer<T> getInstance(Predicate<T> predicate, Transformer<T> trueTransformer) {
		return getInstance(predicate, trueTransformer, NOPTransformer.INSTANCE);
	}

	/**
	 * Factory method that performs validation.
	 * 
	 * @param predicate predicate to switch on, not null
	 * @param trueTransformer transformer used if true, not null
	 * @param falseTransformer transformer used if false, not null
	 * @return the <code>if</code> transformer
	 * @throws IllegalArgumentException if either argument is null
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Transformer<T> getInstance(Predicate<T> predicate, Transformer<T> trueTransformer, Transformer<T> falseTransformer) {
		Validate.notNull(predicate);
		Validate.notNull(trueTransformer);
		Validate.notNull(falseTransformer);
		return new IfTransformer(predicate, trueTransformer, falseTransformer);
	}
	
	/**
	 * Constructor that performs no validation.
	 * 
	 * @param predicate predicate to switch on, not null
	 * @param trueTransformer transformer used if true, not null
	 * @param falseTransformer transformer used if false, not null
	 */
	private IfTransformer(Predicate<T> predicate, Transformer<T> trueTransformer, Transformer<T> falseTransformer) {
		iPredicate = predicate;
		iTrueTransformer = trueTransformer;
		iFalseTransformer = falseTransformer;
	}

	/**
     * Transforms the input object (leaving it unchanged) into some output object.
     *
     * @param input  the object to be transformed, should be left unchanged
     * @return a transformed object
     * @throws ClassCastException (runtime) if the input is the wrong class
     * @throws IllegalArgumentException (runtime) if the input is invalid
     * @throws FunctorException (runtime) if the transform cannot be completed
     */
	public T transform(T input) {
		if (iPredicate.evaluate(input)) {
			return iTrueTransformer.transform(input);
		} else {
			return iFalseTransformer.transform(input);
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
     * Gets the transformer called when true.
     * 
     * @return the closure
     */
	public Transformer<T> getTrueTransformer() {
		return iTrueTransformer;
	}
	
	/**
     * Gets the transformer called when false.
     * 
     * @return the closure
     */
	public Transformer<T> getFalseTransformer() {
		return iFalseTransformer;
	}
}
