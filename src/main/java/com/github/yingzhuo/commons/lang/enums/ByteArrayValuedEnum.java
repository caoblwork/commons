package com.github.yingzhuo.commons.lang.enums;

/**
 * 带有byte[]值的枚举型
 * 
 * @author yingzhuo
 *
 */
public interface ByteArrayValuedEnum extends ValuedEnum {

	/**
	 * 获取值
	 * 
	 * @return 值
	 */
	public byte[] byteArrayValue();

}
