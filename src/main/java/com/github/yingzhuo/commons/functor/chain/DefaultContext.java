/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yingzhuo.commons.functor.chain;


import java.util.Hashtable;
import java.util.Map;

import com.github.yingzhuo.commons.functor.Context;


/**
 * <p>Convenience base class for {@link Context} implementations.</p>
 *
 * <p>In addition to the minimal functionality required by the {@link Context}
 * interface, this class implements the recommended support for
 * <em>Attribute-Property Transparency</em>. This is implemented by
 * analyzing the available JavaBeans properties of this class (or its
 * subclass), exposes them as key-value pairs in the <code>Map</code>,
 * with the key being the name of the property itself.</p>
 *
 * <p><strong>IMPLEMENTATION NOTE</strong> - Because <code>empty</code> is a
 * read-only property defined by the <code>Map</code> interface, it may not
 * be utilized as an attribute key or property name.</p>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 499247 $ $Date: 2007-01-24 04:09:44 +0000 (Wed, 24 Jan 2007) $
 */

public class DefaultContext extends Hashtable<String, Object> implements Context {

    // ------------------------------------------------------------ Constructors


	/** Serial version UID */
	private static final long serialVersionUID = -4191764410216546218L;


	/**
     * Default, no argument constructor.
     */
    public DefaultContext() {
        super();
    }


    /**
     * <p>Initialize the contents of this {@link Context} by copying the
     * values from the specified <code>Map</code>.  Any keys in <code>map</code>
     * that correspond to local properties will cause the setter method for
     * that property to be called.</p>
     *
     * @param map Map whose key-value pairs are added
     *
     * @exception IllegalArgumentException if an exception is thrown
     *  writing a local property value
     * @exception UnsupportedOperationException if a local property does not
     *  have a write method.
     */
    public DefaultContext(Map<String, Object> map) {
        super(map);
        putAll(map);
    }

}
