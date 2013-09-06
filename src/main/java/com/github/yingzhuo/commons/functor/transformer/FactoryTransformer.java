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

import com.github.yingzhuo.commons.functor.Factory;
import com.github.yingzhuo.commons.functor.Transformer;

/**
 * Transformer implementation that calls a Factory and returns the result.
 *
 * @author Matt Hall, John Watkinson, Stephen Colebourne
 * @version $Revision: 1.1 $ $Date: 2005/10/11 17:05:24 $
 * @since Commons Collections 3.0
 */
public class FactoryTransformer <I, T> implements Transformer<I, T>, Serializable {

    /**
     * Serial version UID
     */
    static final long serialVersionUID = -6817674502475353160L;

    /**
     * The factory to wrap
     */
    private final Factory<T> iFactory;

    /**
     * Factory method that performs validation.
     *
     * @param factory the factory to call, not null
     * @return the <code>factory</code> transformer
     * @throws IllegalArgumentException if the factory is null
     */
    public static <K,T> Transformer<K, T> getInstance(Factory<T> factory) {
        if (factory == null) {
            throw new IllegalArgumentException("Factory must not be null");
        }
        return new FactoryTransformer<K, T>(factory);
    }

    /**
     * Constructor that performs no validation.
     * Use <code>getInstance</code> if you want that.
     *
     * @param factory the factory to call, not null
     */
    public FactoryTransformer(Factory<T> factory) {
        super();
        iFactory = factory;
    }

    /**
     * Transforms the input by ignoring the input and returning the result of
     * calling the decorated factory.
     *
     * @param input the input object to transform
     * @return the transformed result
     */
    public T transform(I input) {
        return iFactory.create();
    }

    /**
     * Gets the factory.
     *
     * @return the factory
     * @since Commons Collections 3.1
     */
    public Factory<T> getFactory() {
        return iFactory;
    }

}
