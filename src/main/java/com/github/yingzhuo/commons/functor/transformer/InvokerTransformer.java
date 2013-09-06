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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.github.yingzhuo.commons.exception.FunctorException;
import com.github.yingzhuo.commons.functor.Transformer;

/**
 * Transformer implementation that creates a new object instance by reflection.
 *
 * @author Matt Hall, John Watkinson, Stephen Colebourne
 * @version $Revision: 1.1 $ $Date: 2005/10/11 17:05:24 $
 * @since Commons Collections 3.0
 */
@SuppressWarnings("rawtypes")
public class InvokerTransformer implements Transformer, Serializable {

    /**
     * The serial version
     */
    static final long serialVersionUID = -8653385846894047688L;

    /**
     * The method name to call
     */
    private final String iMethodName;
    /**
     * The array of reflection parameter types
     */
    private final Class[] iParamTypes;
    /**
     * The array of reflection arguments
     */
    private final Object[] iArgs;

    /**
     * Gets an instance of this transformer calling a specific method with no arguments.
     *
     * @param methodName the method name to call
     * @return an invoker transformer
     * @since Commons Collections 3.1
     */
    public static Transformer getInstance(String methodName) {
        if (methodName == null) {
            throw new IllegalArgumentException("The method to invoke must not be null");
        }
        return new InvokerTransformer(methodName);
    }

    /**
     * Gets an instance of this transformer calling a specific method with specific values.
     *
     * @param methodName the method name to call
     * @param paramTypes the parameter types of the method
     * @param args       the arguments to pass to the method
     * @return an invoker transformer
     */
    public static Transformer getInstance(String methodName, Class[] paramTypes, Object[] args) {
        if (methodName == null) {
            throw new IllegalArgumentException("The method to invoke must not be null");
        }
        if (((paramTypes == null) && (args != null)) || ((paramTypes != null) && (args == null)) || ((paramTypes != null) && (args != null) && (paramTypes.length != args.length))) {
            throw new IllegalArgumentException("The parameter types must match the arguments");
        }
        if (paramTypes == null || paramTypes.length == 0) {
            return new InvokerTransformer(methodName);
        } else {
            paramTypes = (Class[]) paramTypes.clone();
            args = (Object[]) args.clone();
            return new InvokerTransformer(methodName, paramTypes, args);
        }
    }

    /**
     * Constructor for no arg instance.
     *
     * @param methodName the method to call
     */
    private InvokerTransformer(String methodName) {
        super();
        iMethodName = methodName;
        iParamTypes = null;
        iArgs = null;
    }

    /**
     * Constructor that performs no validation.
     * Use <code>getInstance</code> if you want that.
     *
     * @param methodName the method to call
     * @param paramTypes the constructor parameter types, not cloned
     * @param args       the constructor arguments, not cloned
     */
    public InvokerTransformer(String methodName, Class[] paramTypes, Object[] args) {
        super();
        iMethodName = methodName;
        iParamTypes = paramTypes;
        iArgs = args;
    }

    /**
     * Transforms the input to result by invoking a method on the input.
     *
     * @param input the input object to transform
     * @return the transformed result, null if null input
     */
    @SuppressWarnings("unchecked")
	public Object transform(Object input) {
        if (input == null) {
            return null;
        }
        try {
            Class cls = input.getClass();
            Method method = cls.getMethod(iMethodName, iParamTypes);
            return method.invoke(input, iArgs);

        } catch (NoSuchMethodException ex) {
            throw new FunctorException("InvokerTransformer: The method '" + iMethodName + "' on '" + input.getClass() + "' does not exist");
        } catch (IllegalAccessException ex) {
            throw new FunctorException("InvokerTransformer: The method '" + iMethodName + "' on '" + input.getClass() + "' cannot be accessed");
        } catch (InvocationTargetException ex) {
            throw new FunctorException("InvokerTransformer: The method '" + iMethodName + "' on '" + input.getClass() + "' threw an exception", ex);
        }
    }

}
