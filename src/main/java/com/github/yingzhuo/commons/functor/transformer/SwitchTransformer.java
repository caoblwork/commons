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

import com.github.yingzhuo.commons.functor.Predicate;
import com.github.yingzhuo.commons.functor.Transformer;
import com.github.yingzhuo.commons.lang.Validate;
import com.github.yingzhuo.commons.lang.tuple.Pair;

/**
 * Transformer implementation calls the transformer whose predicate returns true,
 * like a switch statement.
 */
public class SwitchTransformer<T> implements Transformer<T>, Serializable {

    /** Serial version UID */
    private static final long serialVersionUID = -6404460890903469332L;

    /** The tests to consider */
    private final Predicate<T>[] iPredicates;
    /** The matching transformers to call */
    private final Transformer<T>[] iTransformers;
    /** The default transformer to call if no tests match */
    private final Transformer<T> iDefault;
    
    /**
     * Create a new transformer that calls one of the transformer depending 
     * on the predicates. 
     * <p>
     * The Map consists of Predicate keys and Transformer values. A transformer 
     * is called if its matching predicate returns true. Each predicate is evaluated
     * until one returns true. If no predicates evaluate to true, the default
     * transformer is called. The default transformer is set in the map with a 
     * null key. The ordering is that of the iterator() method on the entryset 
     * collection of the map.
     * 
     * @param defaultTransformer the transformer to use if no match, null means nop
     * @param pairs the pairs of predicates and transformers
     * @return the <code>switch</code> transformer
     * @throws IllegalArgumentException if the map is null
     * @throws IllegalArgumentException if any transformer in the map is null
     */
    @SuppressWarnings("unchecked")
	public static <T> Transformer<T> getInstance(Transformer<T> defaultTransformer, Pair<Predicate<T>, Transformer<T>>... pairs) {
    	
    	Validate.noNullElements(pairs);
    	
    	int size = pairs.length;
    	Predicate<T>[] 		predicates 		= new Predicate[size];
    	Transformer<T>[]	transformers	= new Transformer[size];
    	
    	for (int i = 0; i < size; i ++) {
    		predicates[i]   = pairs[i].getLeft();
    		transformers[i] = pairs[i].getRight();
    	}
    	
    	if (defaultTransformer == null) {
    		defaultTransformer = NOPTransformer.INSTANCE;
    	}
    	
    	Validate.noNullElements(predicates);
    	Validate.noNullElements(transformers);
    	
    	return new SwitchTransformer<T>(predicates, transformers, defaultTransformer);
    }
    
    /**
     * Constructor that performs no validation.
     * Use <code>getInstance</code> if you want that.
     * 
     * @param predicates  array of predicates, not cloned, no nulls
     * @param transformers  matching array of transformers, not cloned, no nulls
     * @param defaultTransformer  the transformer to use if no match, null means return null
     */
    private SwitchTransformer(Predicate<T>[] predicates, Transformer<T>[] transformers, Transformer<T> defaultTransformer) {
        iPredicates = predicates;
        iTransformers = transformers;
        iDefault = defaultTransformer;
    }

    /**
     * Transforms the input to result by calling the transformer whose matching
     * predicate returns true.
     * 
     * @param input  the input object to transform
     * @return the transformed result
     */
    public T transform(T input) {
        for (int i = 0; i < iPredicates.length; i++) {
            if (iPredicates[i].evaluate(input) == true) {
                return iTransformers[i].transform(input);
            }
        }
        return iDefault.transform(input);
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
     * Gets the transformers, do not modify the array.
     * 
     * @return the transformers
     */
    public Transformer<T>[] getTransformers() {
        return iTransformers;
    }

    /**
     * Gets the default transformer.
     * 
     * @return the default transformer
     */
    public Transformer<T> getDefaultTransformer() {
        return iDefault;
    }

}
