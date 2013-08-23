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
package com.github.yingzhuo.commons.functor.transformer;

import java.io.Serializable;

import com.github.yingzhuo.commons.functor.Transformer;
import com.github.yingzhuo.commons.lang.Validate;

/**
 * Transformer implementation that chains the specified transformers together.
 * <p>
 * The input object is passed to the first transformer. The transformed result
 * is passed to the second transformer and so on.
 * 
 * @author Stephen Colebourne
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ChainedTransformer<T> implements Transformer<T>, Serializable {

    /** Serial version UID */
    private static final long serialVersionUID = 3514945074733160196L;

    /** The transformers to call in turn */
    private final Transformer<T>[] iTransformers;

    /**
     * Factory method that performs validation and copies the parameter array.
     * 
     * @param transformers  the transformers to chain, copied, no nulls
     * @return the <code>chained</code> transformer
     * @throws IllegalArgumentException if the transformers array is null
     * @throws IllegalArgumentException if any transformer in the array is null
     */
	public static <T> Transformer<T> getInstance(Transformer<T>... transformers) {
        return new ChainedTransformer(transformers);
    }

    /**
     * Constructor that performs no validation.
     * Use <code>getInstance</code> if you want that.
     * 
     * @param transformers  the transformers to chain, not copied, no nulls
     */
    private ChainedTransformer(Transformer<T>[] transformers) {
        Validate.noNullElements(transformers);
        iTransformers = transformers;
    }

    /**
     * Transforms the input to result via each decorated transformer
     * 
     * @param object  the input object passed to the first transformer
     * @return the transformed result
     */
    public T transform(T object) {
        for (int i = 0; i < iTransformers.length; i++) {
            object = (T) iTransformers[i].transform(object);
        }
        return (T) object;
    }

    /**
     * Gets the transformers, do not modify the array.
     * @return the transformers
     */
    public Transformer<T>[] getTransformers() {
        return iTransformers;
    }

}
