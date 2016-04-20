package com.demo.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AesUtils {
	private static final Logger logger = LoggerFactory.getLogger(AesUtils.class);
	private SecretKeySpec secretKey;
	private static AesUtils aes;
	private static final String DEFAULT_STR = "*****iloveyou!!!";

	public static AesUtils getInstance(String str) {
		if (aes == null) {
			if (null == str || str.length() == 0) {
				aes = new AesUtils(DEFAULT_STR);
			} else {
				aes = new AesUtils(str);
			}
		}
		return aes;
	}

	public static AesUtils getInstance() {
		if (aes == null) {
			aes = new AesUtils(DEFAULT_STR);
		}
		return aes;
	}

	private AesUtils(String str) {
		setKey(str);// generate secret key
	}

	public SecretKey getSecretKey() {
		return secretKey;
	}

	/**
	 * generate KEY
	 */
	public void setKey(String strKey) {
		try {
			byte[] bk = strKey.getBytes(CharEncoding.UTF_8);
			this.secretKey = new SecretKeySpec(bk, "AES");
		} catch (Exception e) {
			logger.error("Encrypt setKey is exception.", e);
		}
	}

	/**
	 * @Description AES encrypt
	 * @param str
	 * @return
	 */
	public String encryptAES(String str) {
		byte[] encryptBytes = null;
		String encryptStr = null;
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
			encryptBytes = cipher.doFinal(str.getBytes());
			if (encryptBytes != null) {
				encryptStr = Base64.encodeBase64String(encryptBytes);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return encryptStr;
	}

	/**
	 * @Description AES decrypt
	 * @param str
	 * @return
	 */
	public String decryptAES(String str) {
		byte[] decryptBytes = null;
		String decryptStr = null;
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
			byte[] scrBytes = Base64.decodeBase64(str);
			decryptBytes = cipher.doFinal(scrBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (decryptBytes != null) {
			decryptStr = new String(decryptBytes);
		}
		return decryptStr;
	}

	/**
	 * AES encrypt
	 */
	public String encrypt(String value, String key) throws Exception {
		setKey(key);
		return encryptAES(value);
	}

	/**
	 * AES decrypt
	 */
	public String decrypt(String value, String key) throws Exception {
		setKey(key);
		return decryptAES(value);
	}

	public static void main(String[] args) {
		AesUtils aes = AesUtils.getInstance(")*$&^&0HJs@k(u#s");
		String enStr = aes.encryptAES("ffffff中434#&@……");
		System.out.println(enStr);

		String deStr = aes.decryptAES(enStr);
		System.out.println(deStr);
	}
}
