/*
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * This package contains generic collection interfaces and implementations, and
 * other utilities for working with collections. It is a part of the open-source
 * <a href="http://guava-libraries.googlecode.com">Guava libraries</a>.
 *
 * <h2>Collection Types</h2>
 *
 * <dl>
 * <dt>{@link com.github.yingzhuo.commons.collections.BiMap}
 * <dd>An extension of {@link java.util.Map} that guarantees the uniqueness of
 *     its values as well as that of its keys. This is sometimes called an
 *     "invertible map," since the restriction on values enables it to support
 *     an {@linkplain com.github.yingzhuo.commons.collections.BiMap#inverse inverse view} --
 *     which is another instance of {@code BiMap}.
 *
 * <dt>{@link com.github.yingzhuo.commons.collections.Multiset}
 * <dd>An extension of {@link java.util.Collection} that may contain duplicate
 *     values like a {@link java.util.List}, yet has order-independent equality
 *     like a {@link java.util.Set}.  One typical use for a multiset is to
 *     represent a histogram.
 *
 * <dt>{@link com.github.yingzhuo.commons.collections.Multimap}
 * <dd>A new type, which is similar to {@link java.util.Map}, but may contain
 *     multiple entries with the same key. Some behaviors of
 *     {@link com.github.yingzhuo.commons.collections.Multimap} are left unspecified and are
 *     provided only by the subtypes mentioned below.
 *
 * <dt>{@link com.github.yingzhuo.commons.collections.ListMultimap}
 * <dd>An extension of {@link com.github.yingzhuo.commons.collections.Multimap} which permits
 *     duplicate entries, supports random access of values for a particular key,
 *     and has <i>partially order-dependent equality</i> as defined by
 *     {@link com.github.yingzhuo.commons.collections.ListMultimap#equals(Object)}. {@code
 *     ListMultimap} takes its name from the fact that the {@linkplain
 *     com.github.yingzhuo.commons.collections.ListMultimap#get collection of values}
 *     associated with a given key fulfills the {@link java.util.List} contract.
 *
 * <dt>{@link com.github.yingzhuo.commons.collections.SetMultimap}
 * <dd>An extension of {@link com.github.yingzhuo.commons.collections.Multimap} which has
 *     order-independent equality and does not allow duplicate entries; that is,
 *     while a key may appear twice in a {@code SetMultimap}, each must map to a
 *     different value.  {@code SetMultimap} takes its name from the fact that
 *     the {@linkplain com.github.yingzhuo.commons.collections.SetMultimap#get collection of
 *     values} associated with a given key fulfills the {@link java.util.Set}
 *     contract.
 *
 * <dt>{@link com.github.yingzhuo.commons.collections.SortedSetMultimap}
 * <dd>An extension of {@link com.github.yingzhuo.commons.collections.SetMultimap} for which
 *     the {@linkplain com.github.yingzhuo.commons.collections.SortedSetMultimap#get
 *     collection values} associated with a given key is a
 *     {@link java.util.SortedSet}.
 *
 * <dt>{@link com.github.yingzhuo.commons.collections.Table}
 * <dd>A new type, which is similar to {@link java.util.Map}, but which indexes
 *     its values by an ordered pair of keys, a row key and column key.
 *
 * <dt>{@link com.github.yingzhuo.commons.collections.ClassToInstanceMap}
 * <dd>An extension of {@link java.util.Map} that associates a raw type with an
 *     instance of that type.
 * </dl>
 *
 * <h2>Collection Implementations</h2>
 *
 * <h3>of {@link java.util.List}</h3>
 * <ul>
 * <li>{@link com.github.yingzhuo.commons.collections.ImmutableList}
 * </ul>
 *
 * <h3>of {@link java.util.Set}</h3>
 * <ul>
 * <li>{@link com.github.yingzhuo.commons.collections.ImmutableSet}
 * <li>{@link com.github.yingzhuo.commons.collections.ImmutableSortedSet}
 * <li>{@link com.github.yingzhuo.commons.collections.ContiguousSet} (see {@code Range})
 * </ul>
 *
 * <h3>of {@link java.util.Map}</h3>
 * <ul>
 * <li>{@link com.github.yingzhuo.commons.collections.ImmutableMap}
 * <li>{@link com.github.yingzhuo.commons.collections.ImmutableSortedMap}
 * <li>{@link com.github.yingzhuo.commons.collections.MapMaker}
 * </ul>
 *
 * <h3>of {@link com.github.yingzhuo.commons.collections.BiMap}</h3>
 * <ul>
 * <li>{@link com.github.yingzhuo.commons.collections.ImmutableBiMap}
 * <li>{@link com.github.yingzhuo.commons.collections.HashBiMap}
 * <li>{@link com.github.yingzhuo.commons.collections.EnumBiMap}
 * <li>{@link com.github.yingzhuo.commons.collections.EnumHashBiMap}
 * </ul>
 *
 * <h3>of {@link com.github.yingzhuo.commons.collections.Multiset}</h3>
 * <ul>
 * <li>{@link com.github.yingzhuo.commons.collections.ImmutableMultiset}
 * <li>{@link com.github.yingzhuo.commons.collections.HashMultiset}
 * <li>{@link com.github.yingzhuo.commons.collections.LinkedHashMultiset}
 * <li>{@link com.github.yingzhuo.commons.collections.TreeMultiset}
 * <li>{@link com.github.yingzhuo.commons.collections.EnumMultiset}
 * <li>{@link com.github.yingzhuo.commons.collections.ConcurrentHashMultiset}
 * </ul>
 *
 * <h3>of {@link com.github.yingzhuo.commons.collections.Multimap}</h3>
 * <ul>
 * <li>{@link com.github.yingzhuo.commons.collections.ImmutableMultimap}
 * <li>{@link com.github.yingzhuo.commons.collections.ImmutableListMultimap}
 * <li>{@link com.github.yingzhuo.commons.collections.ImmutableSetMultimap}
 * <li>{@link com.github.yingzhuo.commons.collections.ArrayListMultimap}
 * <li>{@link com.github.yingzhuo.commons.collections.HashMultimap}
 * <li>{@link com.github.yingzhuo.commons.collections.TreeMultimap}
 * <li>{@link com.github.yingzhuo.commons.collections.LinkedHashMultimap}
 * <li>{@link com.github.yingzhuo.commons.collections.LinkedListMultimap}
 * </ul>
 *
 * <h3>of {@link com.github.yingzhuo.commons.collections.Table}</h3>
 * <ul>
 * <li>{@link com.github.yingzhuo.commons.collections.ImmutableTable}
 * <li>{@link com.github.yingzhuo.commons.collections.ArrayTable}
 * <li>{@link com.github.yingzhuo.commons.collections.HashBasedTable}
 * <li>{@link com.github.yingzhuo.commons.collections.TreeBasedTable}
 * </ul>
 *
 * <h3>of {@link com.github.yingzhuo.commons.collections.ClassToInstanceMap}</h3>
 * <ul>
 * <li>{@link com.github.yingzhuo.commons.collections.ImmutableClassToInstanceMap}
 * <li>{@link com.github.yingzhuo.commons.collections.MutableClassToInstanceMap}
 * </ul>
 *
 * <h2>Classes of static utility methods</h2>
 *
 * <ul>
 * <li>{@link com.github.yingzhuo.commons.collections.Collections2}
 * <li>{@link com.github.yingzhuo.commons.collections.Iterators}
 * <li>{@link com.github.yingzhuo.commons.collections.Iterables}
 * <li>{@link com.github.yingzhuo.commons.collections.Lists}
 * <li>{@link com.github.yingzhuo.commons.collections.Maps}
 * <li>{@link com.github.yingzhuo.commons.collections.Queues}
 * <li>{@link com.github.yingzhuo.commons.collections.Sets}
 * <li>{@link com.github.yingzhuo.commons.collections.Multisets}
 * <li>{@link com.github.yingzhuo.commons.collections.Multimaps}
 * <li>{@link com.github.yingzhuo.commons.collections.Tables}
 * <li>{@link com.github.yingzhuo.commons.collections.ObjectArrays}
 * </ul>
 *
 * <h2>Comparison</h2>
 *
 * <ul>
 * <li>{@link com.github.yingzhuo.commons.collections.Ordering}
 * <li>{@link com.github.yingzhuo.commons.collections.ComparisonChain}
 * </ul>
 *
 * <h2>Abstract implementations</h2>
 *
 * <ul>
 * <li>{@link com.github.yingzhuo.commons.collections.AbstractIterator}
 * <li>{@link com.github.yingzhuo.commons.collections.AbstractSequentialIterator}
 * <li>{@link com.github.yingzhuo.commons.collections.ImmutableCollection}
 * <li>{@link com.github.yingzhuo.commons.collections.UnmodifiableIterator}
 * <li>{@link com.github.yingzhuo.commons.collections.UnmodifiableListIterator}
 * </ul>
 *
 * <h2>Ranges</h2>
 *
 * <ul>
 * <li>{@link com.github.yingzhuo.commons.collections.Range}
 * <li>{@link com.github.yingzhuo.commons.collections.RangeMap}
 * <li>{@link com.github.yingzhuo.commons.collections.DiscreteDomain}
 * <li>{@link com.github.yingzhuo.commons.collections.ContiguousSet}
 * </ul>
 *
 * <h2>Other</h2>
 *
 * <ul>
 * <li>{@link com.github.yingzhuo.commons.collections.Interner},
 *     {@link com.github.yingzhuo.commons.collections.Interners}
 * <li>{@link com.github.yingzhuo.commons.collections.Constraint},
 *     {@link com.github.yingzhuo.commons.collections.Constraints}
 * <li>{@link com.github.yingzhuo.commons.collections.MapConstraint},
 *     {@link com.github.yingzhuo.commons.collections.MapConstraints}
 * <li>{@link com.github.yingzhuo.commons.collections.MapDifference},
 *     {@link com.github.yingzhuo.commons.collections.SortedMapDifference}
 * <li>{@link com.github.yingzhuo.commons.collections.MinMaxPriorityQueue}
 * <li>{@link com.github.yingzhuo.commons.collections.PeekingIterator}
 * </ul>
 *
 * <h2>Forwarding collections</h2>
 *
 * <ul>
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingCollection}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingConcurrentMap}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingIterator}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingList}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingListIterator}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingListMultimap}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingMap}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingMapEntry}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingMultimap}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingMultiset}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingNavigableMap}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingNavigableSet}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingObject}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingQueue}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingSet}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingSetMultimap}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingSortedMap}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingSortedMultiset}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingSortedSet}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingSortedSetMultimap}
 * <li>{@link com.github.yingzhuo.commons.collections.ForwardingTable}
 * </ul>
 */
@javax.annotation.ParametersAreNonnullByDefault
package com.github.yingzhuo.commons.collections;
