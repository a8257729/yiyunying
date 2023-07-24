package com.ztesoft.android.util;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
public class SecurityUtil {
	public static final Logger logger = Logger.getLogger(SecurityUtil.class);

	static Cipher eCipher = null;

	static Cipher dCipher = null;

	static byte[] bkey = null;

	static byte[] biv = null;

	static AlgorithmParameterSpec paramSpec = null;

	static SecretKey keySpec = null;

	static String encoding = "UTF-8";

	static BASE64Decoder base64Decoder = new BASE64Decoder();

	static {
		try {
			eCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			dCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

			bkey = base64Decoder.decodeBuffer("SivnBF2z0IY=");
			biv = base64Decoder.decodeBuffer("uK1EBgjPTr0=");
			paramSpec = new IvParameterSpec(biv);
			keySpec = new SecretKeySpec(bkey, "DES");

			dCipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
			eCipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 
	 * @param decryptString
	 *            String
	 * @return String
	 * @throws Exception
	 */
	public static String decrypt(String decryptString) throws Exception {
		try {
			byte[] bout = null;
			synchronized (dCipher) {
				bout = dCipher.doFinal(base64Decoder
						.decodeBuffer(decryptString));
			}
			return new String(bout, encoding);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	/**
	 * 
	 * @param encryptString
	 *            String
	 * @return String
	 * @throws Exception
	 */
	public static String encrypt(String encryptString) throws Exception {
		try {
			byte[] eout = null;
			synchronized (eCipher) {
				eout = eCipher.doFinal(encryptString.getBytes(encoding));
			}
			String eStr = new BASE64Encoder().encode(eout);
			return new String(eStr);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	public static void main(String[] args) throws Exception {
		String stfId = "528";
		String stfPwd = "1";

		String encPwd = SecurityUtil.encrypt(stfId + stfPwd);

		System.out.println(encPwd);

		String decPwd = SecurityUtil.decrypt("KXJE5UIYyWA=");
		System.out.println(decPwd);
		System.out
				.println("staff id: " + decPwd.substring(0, stfId.length())
						+ " | "
						+ decPwd.substring(stfId.length() , decPwd.length()));
	}

}
