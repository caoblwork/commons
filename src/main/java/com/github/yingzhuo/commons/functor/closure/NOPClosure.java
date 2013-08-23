package com.github.yingzhuo.commons.functor.closure;

import java.io.Serializable;

import com.github.yingzhuo.commons.functor.Closure;

@SuppressWarnings("rawtypes")
public class NOPClosure implements Closure, Serializable {

	/** Serial version UID */
	private static final long serialVersionUID = -3781742419595986725L;

	/** Singleton predicate instance */
	public static final Closure INSTANCE = new NOPClosure();

	/**
	 * Constructor
	 */
	private NOPClosure() {
	}

	/**
	 * Do nothing.
	 * 
	 * @param input the input object
	 */
	public void execute(Object input) {
		// do nothing
	}

}
