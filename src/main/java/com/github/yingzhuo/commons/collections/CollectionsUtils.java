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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.github.yingzhuo.commons.collections.bag.ImmutableBag;
import com.github.yingzhuo.commons.collections.bag.SynchronizedBag;
import com.github.yingzhuo.commons.collections.iterator.ImmutableIterator;

public class CollectionsUtils {

	// name spance COLLECTION
	// -------------------------------------------------------------------------------------------------
	public static final class COLLECTION
	{
		public static <E> Collection<E> asImmutableCollection(Collection<E> collection) {
			return Collections.unmodifiableCollection(collection);
		}
		
		public static <E> Collection<E> asSynchronizedCollection(Collection<E> collection) {
			return Collections.synchronizedCollection(collection);
		}
	}
	
	public static final class BAG {
		
		public static <E> Bag<E> asImmutableBag(Bag<E> bag) {
			return ImmutableBag.decorate(bag);
		}
		
		public static <E> Bag<E> asSynchronizedBag(Bag<E> bag) {
			return SynchronizedBag.decorate(bag);
		}

	}
	
	public static final class LIST 
	{
		public static <E> List<E> asImmutableList(List<E> list) {
			return Collections.unmodifiableList(list);
		}
		
		public static <E> List<E> asSynchronizedList(List<E> list) {
			return Collections.synchronizedList(list);
		}

	}
	
	public static final class ITERATOR {

		public static <E> Iterator<E> asImmutableIterator(Iterator<E> iterator) {
			return ImmutableIterator.decorate(iterator);
		}
		
	}
}
