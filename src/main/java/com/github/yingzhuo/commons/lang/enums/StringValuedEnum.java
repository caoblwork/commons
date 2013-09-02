package com.github.yingzhuo.commons.lang.enums;

/**
 * 带有String值的枚举型
 * 
 * @author yingzhuo
 *
 */
public interface StringValuedEnum extends ValuedEnum {

	/**
	 * 获取值
	 * 
	 * @return 值
	 */
	public String stringValue();

}
