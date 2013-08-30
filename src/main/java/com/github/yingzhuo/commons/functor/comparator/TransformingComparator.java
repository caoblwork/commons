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
package com.github.yingzhuo.commons.functor.comparator;

import java.util.Comparator;

import com.github.yingzhuo.commons.functor.Transformer;

/**
 * Decorates another Comparator with transformation behavior. That is, the
 * return value from the transform operation will be passed to the decorated
 * {@link Comparator#compare(Object,Object) compare} method.
 * 
 * 
 * @see com.github.yingzhuo.commons.functor.Transformer
 * @see com.github.yingzhuo.commons.functor.comparator.ComparableComparator
 */
public class TransformingComparator<T> implements Comparator<T> {
    
    /** The decorated comparator. */
    protected Comparator<T> decorated;
    /** The transformer being used. */    
    protected Transformer<T> transformer;

    //-----------------------------------------------------------------------
    public static <T> Comparator<T> decorate(Transformer<T> transformer, Comparator<T> comparator) {
    	return new TransformingComparator<T>(transformer, comparator);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Constructs an instance with the given Transformer and Comparator.
     * 
     * @param transformer  what will transform the arguments to <code>compare</code>
     * @param comparator  the decorated Comparator
     */
    private TransformingComparator(Transformer<T> transformer, Comparator<T> comparator) {
        this.decorated = comparator;
        this.transformer = transformer;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the result of comparing the values from the transform operation.
     * 
     * @param obj1  the first object to transform then compare
     * @param obj2  the second object to transform then compare
     * @return negative if obj1 is less, positive if greater, zero if equal
     */
    public int compare(T obj1, T obj2) {
        T value1 = this.transformer.transform(obj1);
        T value2 = this.transformer.transform(obj2);
        return this.decorated.compare(value1, value2);
    }

}

