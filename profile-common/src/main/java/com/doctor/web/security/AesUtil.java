package com.doctor.web.security;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author shizi
 *
 * @time 2015年9月22日 上午9:27:20
 */
public final class AesUtil {
	/*
	 * 加密用的Key 可以用26个字母和数字组成
	 * 此处使用AES-128-CBC加密模式，key需要为16位。
	 */
	public static final String ivParameter = "0123456789abcdef";

	public static final String transformation = "AES/CBC/PKCS5Padding";

	public static String generateKeyToBase64String() throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		SecretKey aesKey = kgen.generateKey();
		return Base64.encodeBase64String(aesKey.getEncoded());

	}

	public static byte[] getrKeyFromBase64String(String base64KeyString) {
		return Base64.decodeBase64(base64KeyString);
	}

	public static String encryptToBase64String(String content, String base64KeyString) throws Exception {
		Cipher cipher = Cipher.getInstance(transformation);
		byte[] raw = AesUtil.getrKeyFromBase64String(base64KeyString);
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
		return Base64.encodeBase64String(encrypted);
	}

	public static String decryptFromBase64String(String sSrc, String base64KeyString) throws Exception {

		byte[] raw = AesUtil.getrKeyFromBase64String(base64KeyString);

		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance(transformation);
		IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		byte[] encrypted1 = Base64.decodeBase64(sSrc);
		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original, "utf-8");
		return originalString;
	}

	public static void main(String[] args) throws Exception {

		String cSrc = "我来自www.xhqb2015.org";
		System.out.println(cSrc);
		String keyToBase64String = AesUtil.generateKeyToBase64String();
		System.out.println("key:" + keyToBase64String);

		long lStart = System.currentTimeMillis();
		String enString = AesUtil.encryptToBase64String(cSrc, keyToBase64String);
		System.out.println("加密后的字串是：" + enString);

		long lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("加密耗时：" + lUseTime + "毫秒");

		lStart = System.currentTimeMillis();
		String DeString = AesUtil.decryptFromBase64String(enString, keyToBase64String);
		System.out.println("解密后的字串是：" + DeString);
		lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("解密耗时：" + lUseTime + "毫秒");

	}
}
