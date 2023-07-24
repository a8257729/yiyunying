package com.ztesoft.mobile.v2.util;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class CryptUtils {
	
	protected static final String PASSWORD_SECRET = "nottherealpasscode";
	
	/** ¼ÓÃÜ×Ö·û´® */
	public static String encryptString(String clearText) {
		if(null == clearText) return null;
		
		try {
			DESKeySpec keySpec = new DESKeySpec(
					PASSWORD_SECRET.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(keySpec);

			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			String encrypedPwd = Base64Utils.encodeBytes(cipher.doFinal(clearText
                    .getBytes("UTF-8")));
			return encrypedPwd;
		} catch (Exception e) {
		}
		return clearText;
	}

	/** ½âÃÜ×Ö·û´® */
	public static String decryptString(String encryptedString) {
		if(null == encryptedString) return null;
		
		try { 
			DESKeySpec keySpec = new DESKeySpec(
					PASSWORD_SECRET.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(keySpec);

			byte[] encryptedWithoutB64 = Base64Utils.decode(encryptedString);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] plainTextPwdBytes = cipher.doFinal(encryptedWithoutB64);
			String rtStr = new String(plainTextPwdBytes, "UTF-8");
			return rtStr;
		} catch (Exception e) {
		}
		return encryptedString;
	}

}
