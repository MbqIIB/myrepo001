package com.npst.middleware.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
public static int length = 64;    

//    public static String crypt(final String str) {
//        if (str == null || str.length() == 0) {
//            throw new IllegalArgumentException("String to encrypt cannot be null or zero length");
//        }
//        HashUtil.digester.update(str.getBytes());
//        final byte[] hash = HashUtil.digester.digest();
//        final StringBuffer hexString = new StringBuffer();
//        for (int i = 0; i < hash.length; ++i) {
//            if ((0xFF & hash[i]) < 16) {
//                hexString.append("0" + Integer.toHexString(0xFF & hash[i]));
//            } else {
//               hexString.append(Integer.toHexString(0xFF & hash[i]));
//            }
//        }
//        return hexString.toString();
//    }


public static String crypt( String password,String salt) {
    try {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-512");
        String input = salt + password;
        byte[] result = mDigest.digest(input.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toHexString((result[i] & 0xff) + 0x100).substring(1));
        }
        return sb.toString();
    } catch (Exception e) {
     System.out.println("hashPassword; exception={}" + e);
     e.printStackTrace();
    }
    return null;
}
    
 public static String getSalt() {
     final char[] SALT_CHARS = "0123456789abcdefghijklmnoqrstyvwxyzABCDEFGHIJKLMNOQRSTYVWXYZ".toCharArray();
     SecureRandom random = new SecureRandom();
     StringBuilder sb = new StringBuilder();
     for (int i = 0; i < length; i++) {
         sb.append(SALT_CHARS[random.nextInt(SALT_CHARS.length)]);
     }
     return sb.toString();
 }
   
    public static void main(String[] args)  throws Exception{
	    String salt = getSalt();
	    System.out.println(salt);
		System.out.println(crypt("123456","HQXGY2y47dY4JT6IYrZoQ1XQzwy6I60EGndsmtKDiAVORdxeKYsTK1qdtf9sVzV9"));
	}
}
