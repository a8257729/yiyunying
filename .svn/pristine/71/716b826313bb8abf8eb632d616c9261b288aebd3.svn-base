package com.ztesoft.mobile.common.helper;

import java.io.UnsupportedEncodingException;
import java.security.*;

import sun.misc.BASE64Encoder;

public final class MD5Utils{

	public static String EncoderByMd5(String str){ 
		//确定计算方法 
		MessageDigest md5;
		String newstr = "";
		try {
			md5 = MessageDigest.getInstance("MD5");
		
		BASE64Encoder base64en = new BASE64Encoder(); 
		//加密后的字符串 
		newstr=base64en.encode(md5.digest(str.getBytes("utf-8"))); 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return newstr; 
	}
	
	 public static void main(String[] args){
		System.out.println(MD5Utils.EncoderByMd5("1w"));
	 }
}