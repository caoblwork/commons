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

import java.io.InputStream;
import java.io.Serializable;

import com.github.yingzhuo.commons.exception.FunctorException;
import com.github.yingzhuo.commons.functor.Chain;
import com.github.yingzhuo.commons.functor.Command;
import com.github.yingzhuo.commons.io.IOUtils;
import com.github.yingzhuo.commons.io.LineIterator;
import com.github.yingzhuo.commons.lang.StringUtils;
import com.github.yingzhuo.commons.reflect.ConstructorUtils;

/**
 * You can config commands by a classpath based text file, if you using the {@link ClassPathConfigurationChain}
 * <br>
 * 
 * for example: <code>
 * <pre>
 * Chain chain = new ClassPathConfigurationChain("test.chain");
 * chain.execute(context);
 * </pre>
 * </code>
 * 
 * and content of "test.chain" should like this:
 * <code>
 * <pre>
 * my.pkg.Command1
 * my.pkg.Command2
 * my.pkg.Command3
 * # this is comment line.
 * my.pkg.Command4
 * </pre>
 * </code>
 * 
 * @author YING Zhuo
 *
 */
@SuppressWarnings("unchecked")
public class ClassPathConfigurationChain extends DefaultChain implements Chain, Serializable{

	/** Serial version UID */
	private static final long serialVersionUID = -7707636243742073552L;

	/**
	 * <p>Construct a {@link Chain} configured with the classpath based text file
	 * 
	 * @param configFile classpath base config file
	 */
	public ClassPathConfigurationChain(String configFile) {
		this(configFile, "UTF-8");
	}

	/**
	 * <p>Construct a {@link Chain} configured with the classpath based text file
	 * 
	 * @param configFile classpath base config file
	 * @param encoding encoding of text file
	 */
	public ClassPathConfigurationChain(String configFile, String encoding) {
		InputStream in = null;
		LineIterator iterator = null;

		try {
			
			in = getClass().getClassLoader().getResourceAsStream(configFile);
			iterator = IOUtils.lineIterator(in, encoding);
			
			while (iterator.hasNext()) {
				String className = StringUtils.trimToEmpty(iterator.next());
				if (StringUtils.isBlank(className) || StringUtils.startsWith(className, "#")) {
					continue;
				}

				Class<Command> klass = (Class<Command>) Class.forName(className);
				Command command = ConstructorUtils.invokeConstructor(klass);
				addCommand(command);
			}

		} catch (Exception e) {
			
			throw new FunctorException(e.getMessage(), e);
			
		} finally {
			IOUtils.closeQuietly(in);
		}

	}
}
