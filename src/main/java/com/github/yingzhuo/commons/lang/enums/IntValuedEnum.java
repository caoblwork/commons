package com.github.yingzhuo.commons.lang.enums;

/**
 * 带有int值的枚举型
 * 
 * @author yingzhuo
 *
 */
public interface IntValuedEnum extends ValuedEnum {

	/**
	 * 获取值
	 * 
	 * @return 值
	 */
	public int intValue();
}
