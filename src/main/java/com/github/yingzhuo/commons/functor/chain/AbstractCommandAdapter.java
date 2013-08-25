/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yingzhuo.commons.functor.chain;

import java.io.Serializable;

import com.github.yingzhuo.commons.functor.Chain;
import com.github.yingzhuo.commons.functor.Command;
import com.github.yingzhuo.commons.functor.Context;

/**
 * Adapter of Command
 * 
 * @author yingzhuo
 *
 */
public abstract class AbstractCommandAdapter implements Command, Serializable {

	/** Serial version UID */
	private static final long serialVersionUID = 702294577403784669L;

	/**
     * <p>Execute a unit of processing work to be performed.  This
     * {@link Command} may either complete the required processing
     * and return <code>true</code>, or delegate remaining processing
     * to the next {@link Command} in a {@link Chain} containing this
     * {@link Command} by returning <code>false</code>
     *
     * @param context The {@link Context} to be processed by this
     *  {@link Command}
     *
     * @exception Exception general purpose exception return
     *  to indicate abnormal termination
     * @exception IllegalArgumentException if <code>context</code>
     *  is <code>null</code>
     *
     * @return <code>true</code> if the processing of this {@link Context}
     *  has been completed, or <code>false</code> if the processing
     *  of this {@link Context} should be delegated to a subsequent
     *  {@link Command} in an enclosing {@link Chain}
     */
	public boolean execute(Context context) throws Exception {
		return CONTINUE_PROCESSING;
	}

	/**
     * <p>Execute any cleanup activities, such as releasing resources that
     * were acquired during the <code>execute()</code> method of this
     * {@link Filter} instance.</p>
     *
     * @param exception The <code>Exception</code> (if any) that was thrown
     *  by the last {@link Command} that was executed; otherwise
     *  <code>null</code>
     *
     * @exception IllegalArgumentException if <code>context</code>
     *  is <code>null</code>
     *
     * @return If a non-null <code>exception</code> was "handled" by this
     *  method (and therefore need not be rethrown), return <code>true</code>;
     *  otherwise return <code>false</code>
     */
	public final boolean postprocess(Context context, Exception exception) {
		boolean result = false;
		if (exception != null) {
			handleException(context, exception);
			result = true;
		}
		doPostprocess(context);
		return result;
	}

	/**
	 * handle the exception
	 * 
	 * @param exception the exception will be handle
	 */
	protected void handleException(Context context, Exception exception) {
		// do nothing
	}
	
	/**
	 * do the post process acturly
	 * 
	 * @param context context to be handle
	 */
	protected void doPostprocess(Context context) {
		// do nothing
	}
}
