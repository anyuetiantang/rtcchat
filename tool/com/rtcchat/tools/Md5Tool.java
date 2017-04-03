package com.rtcchat.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class Md5Tool {
	
	public static String encryption(String str) {
		String newStr = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			BASE64Encoder base64en = new BASE64Encoder();
			newStr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			System.out.println("md5 fail");
			e.printStackTrace();
		}
		
		
		
		return newStr;
	}
}
