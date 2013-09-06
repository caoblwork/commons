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

package com.github.yingzhuo.commons.web.cookie;

import javax.servlet.http.Cookie;

import com.github.yingzhuo.commons.functor.Builder;
import com.github.yingzhuo.commons.lang.Validate;

/**
 * Builder of javax.servlet.http.Cookie
 * 
 * @author YING Zhuo
 *
 */
public class CookieBuilder implements Builder<Cookie> {
	
	private String name;
	private String value;

	private String comment;
	private String domain;
	private int maxAge = -1;
	private String path = "/";
	private boolean secure = false;
	private int version = 0;
	private boolean isHttpOnly = false;

	// 构造方法
	// ---------------------------------------------------------------------------------
	public CookieBuilder() {
		super();
	}
	
	// set attributes
	// ---------------------------------------------------------------------------------
	public CookieBuilder name(String name) {
		Validate.notNull(name);
		this.name = name;
		return this;
	}
	
	public CookieBuilder value(String value) {
		Validate.notNull(value);
		this.value = value;
		return this;
	}
	
	public CookieBuilder comment(String purpose) {
		Validate.notNull(purpose);
		this.comment = purpose;
		return this;
	}
	
	public CookieBuilder domain(String domain) {
		Validate.notNull(domain);
		this.domain = domain;
		return this;
	}
	
	public CookieBuilder maxAge(int maxAge) {
		this.maxAge = maxAge;
		return this;
	}
	
	public CookieBuilder path(String path) {
		Validate.notNull(path);
		this.path = path;
		return this;
	}
	
	public CookieBuilder secure(boolean secure) {
		this.secure = secure;
		return this;
	}
	
	public CookieBuilder version(int version) {
		this.version = version;
		return this;
	}
	
	public CookieBuilder httpOnly(boolean httpOnly) {
		this.isHttpOnly = httpOnly;
		return this;
	}
	
	@Override
	public Cookie build() {
		Cookie c = new Cookie(name, value);

		c.setHttpOnly(this.isHttpOnly);
		c.setMaxAge(this.maxAge);
		c.setSecure(this.secure);
		c.setPath(path);
		c.setVersion(version);
		
		if (this.domain != null) {
			c.setDomain(domain);
		}
		
		if (this.comment != null) {
			c.setComment(this.comment);
		}
		
		return c;
	}

}
