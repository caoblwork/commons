/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.yingzhuo.commons.functor.predicate;

import java.io.Serializable;

import com.github.yingzhuo.commons.exception.FunctorException;
import com.github.yingzhuo.commons.functor.Predicate;

/**
 * Predicate implementation that always throws an exception.
 *
 * @author Matt Hall, John Watkinson, Stephen Colebourne
 * @version $Revision: 1.1 $ $Date: 2005/10/11 17:05:24 $
 * @since Commons Collections 3.0
 */
public final class ExceptionPredicate <T> implements Predicate<T>, Serializable {

    /**
     * Serial version UID
     */
    static final long serialVersionUID = 7179106032121985545L;

    /**
     * Singleton predicate instance
     */
    @SuppressWarnings("rawtypes")
	public static final Predicate INSTANCE = new ExceptionPredicate();

    /**
     * Factory returning the singleton instance.
     *
     * @return the singleton instance
     * @since Commons Collections 3.1
     */
	@SuppressWarnings("unchecked")
	public static <T> Predicate<T> getInstance() {
        return INSTANCE;
    }

    /**
     * Restricted constructor.
     */
    private ExceptionPredicate() {
        super();
    }

    /**
     * Evaluates the predicate always throwing an exception.
     *
     * @param object the input object
     * @return never
     * @throws FunctorException always
     */
    public boolean evaluate(T object) {
        throw new FunctorException("ExceptionPredicate invoked");
    }

}
