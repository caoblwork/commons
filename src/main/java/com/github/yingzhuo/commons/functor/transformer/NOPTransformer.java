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

package com.github.yingzhuo.commons.functor.transformer;

import java.io.Serializable;

import com.github.yingzhuo.commons.functor.Transformer;

/**
 * Transformer implementation that does nothing.
 *
 * @author Matt Hall, John Watkinson, Stephen Colebourne
 * @version $Revision: 1.1 $ $Date: 2005/10/11 17:05:24 $
 * @since Commons Collections 3.0
 */
public class NOPTransformer <I> implements Transformer<I, I>, Serializable {

    /**
     * Serial version UID
     */
    static final long serialVersionUID = 2133891748318574490L;

    /**
     * Singleton predicate instance
     */
    @SuppressWarnings("rawtypes")
	public static final Transformer INSTANCE = new NOPTransformer();

    /**
     * Factory returning the singleton instance.
     *
     * @return the singleton instance
     * @since Commons Collections 3.1
     */
	@SuppressWarnings("unchecked")
	public static <T> Transformer<T,T> getInstance() {
        return INSTANCE;
    }

    /**
     * Constructor
     */
    private NOPTransformer() {
        super();
    }

    /**
     * Transforms the input to result by doing nothing.
     *
     * @param input the input object to transform
     * @return the transformed result which is the input
     */
    public I transform(I input) {
        return input;
    }

}
