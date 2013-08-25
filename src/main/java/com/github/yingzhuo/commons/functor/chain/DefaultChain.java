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


import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;


import com.github.yingzhuo.commons.functor.Chain;
import com.github.yingzhuo.commons.functor.Command;
import com.github.yingzhuo.commons.functor.Context;
import com.github.yingzhuo.commons.lang.Validate;



/**
 * <p>Convenience base class for {@link Chain} implementations.</p>
 *
 */
public class DefaultChain implements Chain {

    // ----------------------------------------------------------- Constructors

    /**
     * <p>Construct a {@link Chain} with no configured {@link Command}s.</p>
     */
    public DefaultChain() {
    	super();
    }

    /**
     * <p>Construct a {@link Chain} configured with the specified
     * {@link Command}.</p>
     *
     * @param command The {@link Command} to be configured
     *
     * @exception IllegalArgumentException if <code>command</code>
     *  is <code>null</code>
     */
    public DefaultChain(Command command) {
    	super();
        addCommand(command);
    }


    /**
     * <p>Construct a {@link Chain} configured with the specified
     * {@link Command}s.</p>
     *
     * @param commands The {@link Command}s to be configured
     *
     * @exception IllegalArgumentException if <code>commands</code>,
     *  or one of the individual {@link Command} elements,
     *  is <code>null</code>
     */
    public DefaultChain(Command[] commands) {
//    	Validate.notNull(commands);
    	Validate.noNullElements(commands);
        for (int i = 0; i < commands.length; i++) {
            addCommand(commands[i]);
        }
    }


    /**
     * <p>Construct a {@link Chain} configured with the specified
     * {@link Command}s.</p>
     *
     * @param commands The {@link Command}s to be configured
     *
     * @exception IllegalArgumentException if <code>commands</code>,
     *  or one of the individual {@link Command} elements,
     *  is <code>null</code>
     */
    public DefaultChain(Collection<Command> commands) {
    	Validate.noNullElements(commands);
        Iterator<Command> elements = commands.iterator();
        while (elements.hasNext()) {
            addCommand(elements.next());
        }

    }

    // ----------------------------------------------------- Instance Variables

    /**
     * <p>The list of {@link Command}s configured for this {@link Chain}, in
     * the order in which they may delegate processing to the remainder of
     * the {@link Chain}.</p>
     */
    protected Command[] commands = new Command[0];

    /**
     * <p>Flag indicating whether the configuration of our commands list
     * has been frozen by a call to the <code>execute()</code> method.</p>
     */
    protected boolean frozen = false;

    // ---------------------------------------------------------- Chain Methods

    /**
     * See the {@link Chain} JavaDoc.
     *
     * @param command The {@link Command} to be added
     *
     * @exception IllegalArgumentException if <code>command</code>
     *  is <code>null</code>
     * @exception IllegalStateException if no further configuration is allowed
     */
    public void addCommand(Command command) {

        if (command == null) {
            throw new IllegalArgumentException();
        }
        if (frozen) {
            throw new IllegalStateException();
        }
        Command[] results = new Command[commands.length + 1];
        System.arraycopy(commands, 0, results, 0, commands.length);
        results[commands.length] = command;
        commands = results;

    }

    /**
     * See the {@link Chain} JavaDoc.
     *
     * @param context The {@link Context} to be processed by this
     *  {@link Chain}
     *
     * @throws Exception if thrown by one of the {@link Command}s
     *  in this {@link Chain} but not handled by a <code>postprocess()</code>
     *  method of a {@link Filter}
     * @throws IllegalArgumentException if <code>context</code>
     *  is <code>null</code>
     *
     * @return <code>true</code> if the processing of this {@link Context}
     *  has been completed, or <code>false</code> if the processing
     *  of this {@link Context} should be delegated to a subsequent
     *  {@link Command} in an enclosing {@link Chain}
     */
    public boolean execute(Context context) throws Exception {

        // Verify our parameters
        if (context == null) {
            throw new IllegalArgumentException();
        }

        // Freeze the configuration of the command list
        frozen = true;

        // Execute the commands in this list until one returns true
        // or throws an exception
        boolean saveResult = false;
        Exception saveException = null;
        int i = 0;
        int n = commands.length;
        for (i = 0; i < n; i++) {
            try {
                saveResult = commands[i].execute(context);
                if (saveResult) {
                    break;
                }
            } catch (Exception e) {
                saveException = e;
                break;
            }
        }

        // Call postprocess methods on Filters in reverse order
        if (i >= n) { // Fell off the end of the chain
            i--;
        }
        boolean handled = false;
        boolean result = false;
        for (int j = i; j >= 0; j--) {
            try {
                result =
                    (commands[j]).postprocess(context,
                                                       saveException);
                if (result) {
                    handled = true;
                }
            } catch (Exception e) {
                  // Silently ignore
            }
        }

        // Return the exception or result state from the last execute()
        if ((saveException != null) && !handled) {
            throw saveException;
        } else {
            return (saveResult);
        }

    }

    // -------------------------------------------------------- Package Methods

    /**
     * <p>Return an array of the configured {@link Command}s for this
     * {@link Chain}.  This method is package private, and is used only
     * for the unit tests.</p>
     */
    protected Command[] getCommands() {
        return (commands);
    }

	public boolean postprocess(Context context, Exception exception) {
		boolean handled = false;
		for (int i = commands.length - 1; i >= 0; i--) {
			boolean b = commands[i].postprocess(context, exception);
			if (b == true) {
				handled = true;
			}
		}
		return handled;
	}

	public Iterator<Command> iterator() {
		return Arrays.asList(commands).iterator();
	}

}
