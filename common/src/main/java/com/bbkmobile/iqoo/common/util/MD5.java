/**
 * 
 */
package com.bbkmobile.iqoo.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wangbo
 * @version 1.0.0.0/2011-8-8
 */
public class MD5 {

    private static final byte RESULT_32 = 32;
    private static final byte RESULT_16 = 16;

    public static String encode32(String text) {
        return encodeText(text, RESULT_32);
    }

    public static String encode16(String text) {
        return encodeText(text, RESULT_16);
    }

    public static String encode32(byte[] bytes) {

        return encodeBytes(bytes, RESULT_32);
    }

    public static String encode16(byte[] bytes) {

        return encodeBytes(bytes, RESULT_16);
    }

    private static String encode(byte[] bytes, byte result) {

        String resultStr = encode(bytes);

        if (RESULT_32 == result) {
            return resultStr;
        }
        return resultStr.substring(8, 24);

    }

    private static String encodeBytes(byte[] bytes, byte result) {
        byte[] originalBytes = null;

        try {
            originalBytes = getBytes(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return encode(originalBytes, result);
    }

    private static String encodeText(String plainText, byte result) {
        byte[] originalBytes = null;

        try {
            originalBytes = getBytes(plainText);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return encode(originalBytes, result);
    }

    private static String encode(byte bytes[]) {

        int i;
        StringBuffer buf = new StringBuffer();
        for (int offset = 0; offset < bytes.length; offset++) {
            i = bytes[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }

        return buf.toString();
    }

    private static byte[] getBytes(String plainText)
            throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(plainText.getBytes());
        return md5.digest();
    }

    private static byte[] getBytes(byte[] bytes)
            throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(bytes);
        return md5.digest();
    }
    /*
     * public static String getMD5(byte[] source) { String s = null; char
     * hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
     * 'b', 'c', 'd', 'e', 'f' };// 用来将字节转换成16进制表示的字符 try {
     * java.security.MessageDigest md = java.security.MessageDigest
     * .getInstance("MD5"); md.update(source); byte tmp[] = md.digest();// MD5
     * 的计算结果是一个 128 位的长整数， // 用字节表示就是 16 个字节 char str[] = new char[16 * 2];//
     * 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16 // 进制需要 32 个字符 int k = 0;//
     * 表示转换结果中对应的字符位置 for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节//
     * 转换成 16 // 进制字符的转换 byte byte0 = tmp[i];// 取第 i 个字节 str[k++] =
     * hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,// >>> // 为逻辑右移，将符号位一起右移
     * str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换
     * 
     * } s = new String(str);// 换后的结果转换为字符串
     * 
     * } catch (NoSuchAlgorithmException e) { // TODO Auto-generated catch block
     * e.printStackTrace(); } return s; }
     * 
     * //对字符串进行MD5加密 public static String getMD5Str(String str) { MessageDigest
     * messageDigest = null;
     * 
     * try { messageDigest = MessageDigest.getInstance("MD5");
     * messageDigest.reset(); messageDigest.update(str.getBytes("UTF-8")); }
     * catch (NoSuchAlgorithmException e) { e.printStackTrace(); return null; }
     * catch (UnsupportedEncodingException e) { e.printStackTrace(); }
     * 
     * byte[] byteArray = messageDigest.digest();
     * 
     * StringBuffer md5StrBuff = new StringBuffer();
     * 
     * for (int i = 0; i < byteArray.length; i++) { if (Integer.toHexString(0xFF
     * & byteArray[i]).length() == 1)
     * md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
     * else md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i])); }
     * 
     * return md5StrBuff.toString(); }
     */
    // public static void main(String[] args){
    // System.out.println(MD5.encode32("abc")); //
    // 900150983cd24fb0d6963f7d28e17f72
    // System.out.println(MD5.encode32("abc".getBytes())); //
    // 900150983cd24fb0d6963f7d28e17f72
    // System.out.println(MD5.encode32("")); // d41d8cd98f00b204e9800998ecf8427e
    // System.out.println(MD5.encode32("".getBytes())); //
    // d41d8cd98f00b204e9800998ecf8427e
    // System.out.println(MD5.encode32("abcdefghijklmnopqrstuvwxyz")); //
    // c3fcd3d76192e4007dfb496cca67e13b
    // System.out.println(MD5.encode32("abcdefghijklmnopqrstuvwxyz".getBytes()));
    // // c3fcd3d76192e4007dfb496cca67e13b
    // System.out.println(MD5.encode32("message digest"));//
    // f96b697d7cb7938d525a2f31aaf161d0
    // System.out.println(MD5.encode32("message digest".getBytes()));//
    // f96b697d7cb7938d525a2f31aaf161d0
    // System.out.println("-----------");
    // System.out.println(getMD5("20131030172503461007".getBytes()));
    // System.out.println(MD5.encode16("abc")); //
    // 900150983cd24fb0d6963f7d28e17f72
    // System.out.println(getMD5Str("20131030172503461007")); //
    // 900150983cd24fb0d6963f7d28e17f72
    // }
    //

}
