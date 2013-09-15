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
package com.github.yingzhuo.commons.functor.comparator;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A {@link Comparator} for {@link Boolean} objects that can sort by 
 * their string representations。
 * 
 * @author 应卓
 *
 */
public class ToStringComparator<T> implements Comparator<T>, Serializable {

	/** Serialization version. */
	private static final long serialVersionUID = -1620804854258492938L;
	
	private boolean ignoreCase = false;
	
	// Constructors
    //-----------------------------------------------------------------------
    /** 
     * Constructor
     */
	public ToStringComparator() {
		super();
	}
	
	public ToStringComparator(boolean ignoreCase) {
		this();
		this.ignoreCase = ignoreCase;
	}

	@Override
	public int compare(T o1, T o2) {
		if (ignoreCase) {
			return o1.toString().compareToIgnoreCase(o2.toString());
		} else {
			return o1.toString().compareTo(o2.toString());
		}
	}

}
