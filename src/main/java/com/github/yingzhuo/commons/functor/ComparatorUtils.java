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
package com.github.yingzhuo.commons.functor;

import java.util.Collection;
import java.util.Comparator;

import com.github.yingzhuo.commons.functor.comparator.BooleanComparator;
import com.github.yingzhuo.commons.functor.comparator.ComparableComparator;
import com.github.yingzhuo.commons.functor.comparator.ComparatorChain;
import com.github.yingzhuo.commons.functor.comparator.NullComparator;
import com.github.yingzhuo.commons.functor.comparator.ReverseComparator;
import com.github.yingzhuo.commons.functor.comparator.TransformingComparator;
import com.github.yingzhuo.commons.lang.Validate;

/**
 * Provides convenient static utility methods for <Code>Comparator</Code>
 * objects.
 * <p>
 * Most of the functionality in this class can also be found in the 
 * <code>comparators</code> package. This class merely provides a 
 * convenient central place if you have use for more than one class
 * in the <code>comparators</code> subpackage.
 *
 */
public class ComparatorUtils {

    /**
     * ComparatorUtils should not normally be instantiated.
     */
    public ComparatorUtils() {
    }

    /**
     * Gets a comparator that uses the natural order of the objects.
     * 
     * @param klass  class of some type 
     * @return a comparator which uses natural order
     */
    @SuppressWarnings("unchecked")
	public static <T> Comparator<T> naturalComparator(Class<? extends Comparator<T>> klass) {
    	return ComparableComparator.INSTANCE;
    }

    /**
     * Gets a comparator that compares using two {@link Comparator}s.
     * <p>
     * The second comparator is used if the first comparator returns equal.
     *
     * @param comparator1  the first comparator to use, not null
     * @param comparator2  the first comparator to use, not null
     * @return a {@link ComparatorChain} formed from the two comparators
     * @throws NullPointerException if either comparator is null
     * @see ComparatorChain
     */
    @SuppressWarnings("unchecked")
	public static <T> Comparator<T> chainedComparator(Comparator<T> comparator1, Comparator<T> comparator2) {
        return chainedComparator(new Comparator[] {comparator1, comparator2});
    }

    /**
     * Gets a comparator that compares using an array of {@link Comparator}s, applied
     * in sequence until one returns not equal or the array is exhausted.
     *
     * @param comparators  the comparators to use, not null or empty or containing nulls
     * @return a {@link ComparatorChain} formed from the input comparators
     * @throws NullPointerException if comparators array is null or contains a null
     * @see ComparatorChain
     */
    public static <T> Comparator<T> chainedComparator(Comparator<T>[] comparators) {
        ComparatorChain<T> chain = new ComparatorChain<T>();
        for (int i = 0; i < comparators.length; i++) {
            if (comparators[i] == null) {
                throw new NullPointerException("Comparator cannot be null");
            }
            chain.addComparator(comparators[i]);
        }
        return chain;
    }

    /**
     * Gets a comparator that compares using a collection of {@link Comparator}s,
     * applied in (default iterator) sequence until one returns not equal or the 
     * collection is exhausted.
     *
     * @param comparators  the comparators to use, not null or empty or containing nulls
     * @return a {@link ComparatorChain} formed from the input comparators
     * @throws NullPointerException if comparators collection is null or contains a null
     * @throws ClassCastException if the comparators collection contains the wrong object type
     * @see ComparatorChain
     */
    @SuppressWarnings("unchecked")
	public static <T> Comparator<T> chainedComparator(Collection<Comparator<T>> comparators) {
        return chainedComparator(
            (Comparator[]) comparators.toArray(new Comparator[comparators.size()])
        );
    }

    /**
     * Gets a comparator that reverses the order of the given comparator.
     *
     * @param comparator  the comparator to reverse
     * @return  a comparator that reverses the order of the input comparator
     * @see ReverseComparator
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Comparator<T> reversedComparator(Comparator<T> comparator) {
        Validate.notNull(comparator);
        return new ReverseComparator(comparator);
    }

    /**
     * Gets a Comparator that can sort Boolean objects.
     * <p>
     * The parameter specifies whether true or false is sorted first.
     * <p>
     * The comparator throws NullPointerException if a null value is compared.
     * 
     * @param trueFirst  when <code>true</code>, sort 
     *        <code>true</code> {@link Boolean}s before
     *        <code>false</code> {@link Boolean}s.
     * @return  a comparator that sorts booleans
     */
    @SuppressWarnings("unchecked")
	public static <T> Comparator<T> booleanComparator(boolean trueFirst) {
        return (Comparator<T>) BooleanComparator.getBooleanComparator(trueFirst);
    }
    
    /**
     * Gets a Comparator that controls the comparison of <code>null</code> values.
     * <p>
     * The returned comparator will consider a null value to be less than
     * any nonnull value, and equal to any other null value.  Two nonnull
     * values will be evaluated with the given comparator.
     *
     * @param comparator the comparator that wants to allow nulls
     * @return  a version of that comparator that allows nulls
     * @see NullComparator
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Comparator<T> nullLowComparator(Comparator<T> comparator) {
        Validate.notNull(comparator);
        return new NullComparator(comparator, false);
    }

    /**
     * Gets a Comparator that controls the comparison of <code>null</code> values.
     * <p>
     * The returned comparator will consider a null value to be greater than
     * any nonnull value, and equal to any other null value.  Two nonnull
     * values will be evaluated with the given comparator.
     *
     * @param comparator the comparator that wants to allow nulls
     * @return  a version of that comparator that allows nulls
     * @see NullComparator
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> Comparator<T> nullHighComparator(Comparator<T> comparator) {
    	Validate.notNull(comparator);
        return new NullComparator(comparator, true);
    }

    /**
     * Gets a Comparator that passes transformed objects to the given comparator.
     * <p>
     * Objects passed to the returned comparator will first be transformed
     * by the given transformer before they are compared by the given
     * comparator.
     *
     * @param comparator  the sort order to use
     * @param transformer  the transformer to use
     * @return  a comparator that transforms its input objects before comparing them
     * @see  TransformingComparator
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Comparator<T> transformedComparator(Comparator<T> comparator, Transformer<T> transformer) {
        return new TransformingComparator(transformer, comparator);
    }
}
