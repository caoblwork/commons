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
package com.github.yingzhuo.commons.codec.rsa;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAUtils {

	/** 私有构造方法 */
	private RSAUtils() {
		super();
	}
	
	private static final Cipher CIPHER;
	
	static {
		try {
			CIPHER = Cipher.getInstance("RSA");
		} catch (Throwable e) {
			throw new InternalError(e.getMessage());
		}
	}

	//~ -----------------------------------------------------------------------------
	
	/**
	 * 生成私钥和公钥
	 * 
	 * @return KeyPair
	 */
	public static KeyPair generateKeyPair() {
		return generateKeyPair(1024);
	}
	
	/**
	 * 生成私钥和公钥
	 * 
	 * @param keySize 密钥长度
	 * @return KeyPair
	 */
	public static KeyPair generateKeyPair(int keySize) {
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			throw new InternalError(e.getMessage());
		}
		keyPairGen.initialize(keySize);
		return keyPairGen.generateKeyPair();
	}
	
	/**
	 * 从KeyPair中获得公钥
	 * 
	 * @param keyPair KeyPair
	 * @return 公钥
	 */
	public static PublicKey getPublicKey(KeyPair keyPair) {
		if (keyPair == null) {
			return null;
		}
		return keyPair.getPublic();
	}
	
	/**
	 * 从KeyPair中获得私钥
	 * 
	 * @param keyPair KeyPair
	 * @return 私钥
	 */
	public static PrivateKey getPrivateKey(KeyPair keyPair) {
		if (keyPair == null) {
			return null;
		}
		return keyPair.getPrivate();
	}
	
	/**
	 * 将公钥或私钥转换为String
	 * 
	 * @param key 公钥/私钥
	 * @return 字符串
	 */
	public static String getKeyString(Key key) {
		if (key == null) {
			return null;
		}
		byte[] keyBytes = key.getEncoded();
		String s = (new BASE64Encoder()).encode(keyBytes);
		return s;
	}
	
	/**
	 * 得到公钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key)  {
		byte[] keyBytes = null;
		try {
			keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			throw new InternalError(e.getMessage());
		}
		PublicKey publicKey = null;
		try {
			publicKey = keyFactory.generatePublic(keySpec);
		} catch (InvalidKeySpecException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
		return publicKey;
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) {
		byte[] keyBytes;
		try {
			keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			throw new InternalError(e.getMessage());
		}
		PrivateKey privateKey = null;
		try {
			privateKey = keyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
		return privateKey;
	}
	
	/**
	 * 加密数据
	 * 
	 * @param data 原文
	 * @param key 公钥/私钥
	 * @return 密文
	 */
	public static byte[] encrypt(byte[] data, Key key) {
		try {
			CIPHER.init(Cipher.ENCRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
		
		byte[] result = null;

		try {
			result = CIPHER.doFinal(data);
		} catch (IllegalBlockSizeException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		} catch (BadPaddingException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 解密数据
	 * 
	 * @param data 密文
	 * @param key 公钥/私钥
	 * @return 原文
	 */
	public static byte[] decrypt(byte[] data, Key key) {
		try {
			CIPHER.init(Cipher.DECRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
		
		byte[] result = null;
		
		try {
			result = CIPHER.doFinal(data);
		} catch (IllegalBlockSizeException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		} catch (BadPaddingException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
		
		return result;
	}

}

