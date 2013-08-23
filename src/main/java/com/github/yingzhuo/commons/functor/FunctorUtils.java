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

import com.github.yingzhuo.commons.functor.closure.ChainedClosure;
import com.github.yingzhuo.commons.functor.closure.ForClosure;
import com.github.yingzhuo.commons.functor.closure.IfClosure;
import com.github.yingzhuo.commons.functor.closure.NOPClosure;
import com.github.yingzhuo.commons.functor.closure.SwitchClosure;
import com.github.yingzhuo.commons.functor.closure.WhileClosure;
import com.github.yingzhuo.commons.functor.predicate.AllPredicate;
import com.github.yingzhuo.commons.functor.predicate.AndPredicate;
import com.github.yingzhuo.commons.functor.predicate.AnyPredicate;
import com.github.yingzhuo.commons.functor.predicate.EqualsPredicate;
import com.github.yingzhuo.commons.functor.predicate.FalsePredicate;
import com.github.yingzhuo.commons.functor.predicate.NonePredicate;
import com.github.yingzhuo.commons.functor.predicate.NotPredicate;
import com.github.yingzhuo.commons.functor.predicate.OrPredicate;
import com.github.yingzhuo.commons.functor.predicate.TruePredicate;
import com.github.yingzhuo.commons.functor.predicate.XorPredicate;
import com.github.yingzhuo.commons.functor.transformer.ChainedTransformer;
import com.github.yingzhuo.commons.functor.transformer.NOPTransformer;
import com.github.yingzhuo.commons.lang.tuple.Pair;

@SuppressWarnings("unchecked")
public final class FunctorUtils {

	// CLOSURE
	// -----------------------------------------------------------------------------------------------------------------------
	public static final class CLOSURE {
		
		public static <T> Closure<T> nop() {
			return NOPClosure.INSTANCE;
		}
		
		public static <T> Closure<T> chain(Closure<T>...closures) {
			return ChainedClosure.getInstance(closures);
		}
		
		public static <T> Closure<T> forLoop(int count, Closure<T> closure) {
			return ForClosure.getInstance(count, closure);
		}
		
		public static <T> Closure<T> whileLoop(Predicate<T> predicate, Closure<T> closure, boolean doLoop) {
			return WhileClosure.getInstance(predicate, closure, doLoop);
		}
		
		public static <T> Closure<T> ifElse(Predicate<T> predicate, Closure<T> trueClosure) {
			return IfClosure.getInstance(predicate, trueClosure);
		}
		
		public static <T> Closure<T> ifElse(Predicate<T> predicate, Closure<T> trueClosure, Closure<T> falseClosure) {
			if (falseClosure == null) {
				return IfClosure.getInstance(predicate, trueClosure);
			} else {
				return IfClosure.getInstance(predicate, trueClosure, falseClosure);
			}
		}
		
		public static <T> Closure<T> switchDefault(Closure<T> defaultClosure, Pair<Predicate<T>, Closure<T>>...pairs) {
			return SwitchClosure.getInstance(defaultClosure, pairs);
		}
	}
	
	// PREDICATE
	// -----------------------------------------------------------------------------------------------------------------------
	public static final class PREDICATE {

		public static <T> Predicate<T> alwaysTrue() {
			return TruePredicate.INSTANCE;
		}
		
		public static <T> Predicate<T> alwaysFalse() {
			return FalsePredicate.INSTANCE;
		}
		
		public static <T> Predicate<T> not(Predicate<T> predicate) {
			return NotPredicate.getInstance(predicate);
		}
		
		public static <T> Predicate<T> equals(Predicate<T> predicate1, Predicate<T> predicate2) {
			return EqualsPredicate.getInstance(predicate1, predicate2);
		}
		
		public static <T> Predicate<T> or(Predicate<T> predicate1, Predicate<T> predicate2) {
			return OrPredicate.getInstance(predicate1, predicate2);
		}
		
		public static <T> Predicate<T> and(Predicate<T> predicate1, Predicate<T> predicate2) {
			return AndPredicate.getInstance(predicate1, predicate2);
		}
		
		public static <T> Predicate<T> xor(Predicate<T> predicate1, Predicate<T> predicate2) {
			return XorPredicate.getInstance(predicate1, predicate2);
		}
		
		public static <T> Predicate<T> any(Predicate<T>... predicates) {
			return AnyPredicate.getInstance(predicates);
		}
		
		public static <T> Predicate<T> all(Predicate<T>... predicates) {
			return AllPredicate.getInstance(predicates);
		}

		public static <T> Predicate<T> none(Predicate<T>... predicates) {
			return NonePredicate.getInstance(predicates);
		}
	}
	
	// TRANSFORMER
	// -----------------------------------------------------------------------------------------------------------------------
	public static final class TRANSFORMER {

		public static <T> Transformer<T> nop() {
			return NOPTransformer.INSTANCE;
		}
		
		public static <T> Transformer<T> chain(Transformer<T>... transformers) {
			return ChainedTransformer.getInstance(transformers);
		}

	}

}
