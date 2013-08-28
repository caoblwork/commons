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
package com.github.yingzhuo.commons.collections;

import com.github.yingzhuo.commons.collections.bag.ImmutableBag;
import com.github.yingzhuo.commons.collections.bag.ImmutableSortedBag;
import com.github.yingzhuo.commons.collections.bag.PredicatedBag;
import com.github.yingzhuo.commons.collections.bag.PredicatedSortedBag;
import com.github.yingzhuo.commons.collections.bag.SynchronizedBag;
import com.github.yingzhuo.commons.collections.bag.SynchronizedSortedBag;
import com.github.yingzhuo.commons.collections.bag.TransformedBag;
import com.github.yingzhuo.commons.collections.bag.TransformedSortedBag;
import com.github.yingzhuo.commons.functor.Predicate;
import com.github.yingzhuo.commons.functor.Transformer;

/**
 * Provides utility methods and decorators for
 * {@link Bag} and {@link SortedBag} instances.
 *
 */
public class BagUtils {

    /**
     * Instantiation of BagUtils is not intended or required.
     * However, some tools require an instance to operate.
     */
    public BagUtils() {
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a synchronized (thread-safe) bag backed by the given bag.
     * In order to guarantee serial access, it is critical that all 
     * access to the backing bag is accomplished through the returned bag.
     * <p>
     * It is imperative that the user manually synchronize on the returned
     * bag when iterating over it:
     *
     * <pre>
     * Bag bag = BagUtils.synchronizedBag(new HashBag());
     * ...
     * synchronized(bag) {
     *     Iterator i = bag.iterator(); // Must be in synchronized block
     *     while (i.hasNext())
     *         foo(i.next());
     *     }
     * }
     * </pre>
     *
     * Failure to follow this advice may result in non-deterministic 
     * behavior.
     *
     * @param bag  the bag to synchronize, must not be null
     * @return a synchronized bag backed by that bag
     * @throws IllegalArgumentException  if the Bag is null
     */
    public static <E> Bag<E> synchronizedBag(Bag<E> bag) {
        return SynchronizedBag.decorate(bag);
    }

    /**
     * Returns an immutable view of the given bag.  Any modification
     * attempts to the returned bag will raise an 
     * {@link UnsupportedOperationException}.
     *
     * @param bag  the bag whose immutable view is to be returned, must not be null
     * @return an immutable view of that bag
     * @throws IllegalArgumentException  if the Bag is null
     */
    public static <E> Bag<E> immutableBag(Bag<E> bag) {
        return ImmutableBag.decorate(bag);
    }
    
    /**
     * Returns a predicated (validating) bag backed by the given bag.
     * <p>
     * Only objects that pass the test in the given predicate can be added to the bag.
     * Trying to add an invalid object results in an IllegalArgumentException.
     * It is important not to use the original bag after invoking this method,
     * as it is a backdoor for adding invalid objects.
     *
     * @param bag  the bag to predicate, must not be null
     * @param predicate  the predicate for the bag, must not be null
     * @return a predicated bag backed by the given bag
     * @throws IllegalArgumentException  if the Bag or Predicate is null
     */
    public static <E> Bag<E> predicatedBag(Bag<E> bag, Predicate<E> predicate) {
        return PredicatedBag.decorate(bag, predicate);
    }
    
    /**
     * Returns a transformed bag backed by the given bag.
     * <p>
     * Each object is passed through the transformer as it is added to the
     * Bag. It is important not to use the original bag after invoking this 
     * method, as it is a backdoor for adding untransformed objects.
     *
     * @param bag  the bag to predicate, must not be null
     * @param transformer  the transformer for the bag, must not be null
     * @return a transformed bag backed by the given bag
     * @throws IllegalArgumentException  if the Bag or Transformer is null
     */
    public static <E> Bag<E> transformedBag(Bag<E> bag, Transformer<E> transformer) {
        return TransformedBag.decorate(bag, transformer);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Returns a synchronized (thread-safe) sorted bag backed by the given 
     * sorted bag.
     * In order to guarantee serial access, it is critical that all 
     * access to the backing bag is accomplished through the returned bag.
     * <p>
     * It is imperative that the user manually synchronize on the returned
     * bag when iterating over it:
     *
     * <pre>
     * SortedBag bag = BagUtils.synchronizedSortedBag(new TreeBag());
     * ...
     * synchronized(bag) {
     *     Iterator i = bag.iterator(); // Must be in synchronized block
     *     while (i.hasNext())
     *         foo(i.next());
     *     }
     * }
     * </pre>
     *
     * Failure to follow this advice may result in non-deterministic 
     * behavior.
     *
     * @param bag  the bag to synchronize, must not be null
     * @return a synchronized bag backed by that bag
     * @throws IllegalArgumentException  if the SortedBag is null
     */
    public static <E> SortedBag<E> synchronizedSortedBag(SortedBag<E> bag) {
        return SynchronizedSortedBag.decorate(bag);
    }
    
    /**
     * Returns an immutable view of the given sorted bag.  Any modification
     * attempts to the returned bag will raise an 
     * {@link UnsupportedOperationException}.
     *
     * @param bag  the bag whose immutable view is to be returned, must not be null
     * @return an immutable view of that bag
     * @throws IllegalArgumentException  if the SortedBag is null
     */
    public static <E> SortedBag<E> immutableSortedBag(SortedBag<E> bag) {
        return ImmutableSortedBag.decorate(bag);
    }
    
    /**
     * Returns a predicated (validating) sorted bag backed by the given sorted bag.
     * <p>
     * Only objects that pass the test in the given predicate can be added to the bag.
     * Trying to add an invalid object results in an IllegalArgumentException.
     * It is important not to use the original bag after invoking this method,
     * as it is a backdoor for adding invalid objects.
     *
     * @param bag  the sorted bag to predicate, must not be null
     * @param predicate  the predicate for the bag, must not be null
     * @return a predicated bag backed by the given bag
     * @throws IllegalArgumentException  if the SortedBag or Predicate is null
     */
    public static <E> SortedBag<E> predicatedSortedBag(SortedBag<E> bag, Predicate<E> predicate) {
        return PredicatedSortedBag.decorate(bag, predicate);
    }
    
    
    /**
     * Returns a transformed sorted bag backed by the given bag.
     * <p>
     * Each object is passed through the transformer as it is added to the
     * Bag. It is important not to use the original bag after invoking this 
     * method, as it is a backdoor for adding untransformed objects.
     *
     * @param bag  the bag to predicate, must not be null
     * @param transformer  the transformer for the bag, must not be null
     * @return a transformed bag backed by the given bag
     * @throws IllegalArgumentException  if the Bag or Transformer is null
     */
    public static <E> SortedBag<E> transformedSortedBag(SortedBag<E> bag, Transformer<E> transformer) {
        return TransformedSortedBag.decorate(bag, transformer);
    }
    
}
