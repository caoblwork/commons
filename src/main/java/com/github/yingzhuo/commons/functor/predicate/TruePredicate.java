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
package com.github.yingzhuo.commons.functor.predicate;

import java.io.Serializable;

import com.github.yingzhuo.commons.functor.Predicate;

/**
 * Predicate implementation that always returns true.
 * 
 * @author Stephen Colebourne
 */
@SuppressWarnings({"rawtypes"})
public final class TruePredicate<T> implements Predicate<T>, Serializable {

    /** Serial version UID */
    private static final long serialVersionUID = 3374767158756189740L;
    
    /** Singleton predicate instance */
	public static final Predicate INSTANCE = new TruePredicate();

    /**
     * Restricted constructor.
     */
    private TruePredicate() {
        super();
    }

    /**
     * Evaluates the predicate returning true always.
     * 
     * @param object  the input object
     * @return true always
     */
    public boolean evaluate(Object object) {
        return true;
    }

}
