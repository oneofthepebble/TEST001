package com.project.encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;



public class SecretSHA256 {
	public static void main(String[] args) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
		SecretSHA256 secreat=new SecretSHA256();
		String ss=secreat.aesEncode("123415");
		System.out.println(ss+"암호");
		String SS=secreat.aesDecode(ss);
		System.out.println(SS+"복호");
	}

	private Key keySpec;
	private String iv;
	private String key = "11111111111111111";

	public SecretSHA256() {//throws UnsupportedEncodingException{//,Exception, UnsupportedEncodingException,SchoolException{
	try {	
	iv = this.key.substring(0, 16);
	byte[] keyBytes = new byte[16];
	byte[] b = iv.getBytes("UTF-8");
	int len = b.length;
	if(len > keyBytes.length)
	len = keyBytes.length;
	System.arraycopy(b, 0, keyBytes, 0, len);
	SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
	
	this.keySpec = keySpec;
	}catch(Exception e) {e.printStackTrace();}
	}

	

	public String aesEncode(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException,
	NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,UnsupportedEncodingException,
	IllegalBlockSizeException, BadPaddingException {
	Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
	c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(this.iv.getBytes()));

	byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
	String enStr = new String(Base64.encodeBase64(encrypted));

	return enStr;
	}
	
	//복호화
	public String aesDecode(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException,
	NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException,
	IllegalBlockSizeException, BadPaddingException {
	Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
	c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(this.iv.getBytes("UTF-8")));

	byte[] byteStr = Base64.decodeBase64(str.getBytes());

	String st = new String(c.doFinal(byteStr),"UTF-8");
	return st;
	}	
//		secretSHA256 sS=new secretSHA256();
//		System.out.println(sS.aesEncode("!23124"));
//		System.out.println(sS.aesDecode(sS.aesEncode("!23124")));


	
	
}
