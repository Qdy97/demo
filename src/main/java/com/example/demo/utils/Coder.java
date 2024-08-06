package com.example.demo.utils;


import org.apache.commons.codec.binary.Base64;

public class Coder {

    /**
     * base64编码
     * @param key 数组密钥
     * @return 转化为字符串的密钥
     */
    public static String encryptBASE64(byte[] key){
        return Base64.encodeBase64String(key);
    }

    /**
     * base64解码
     * @param key 字符串密钥
     * @return 转化为数组的密钥
     */
    public static byte[] decodeBASE64(String key){
        return Base64.decodeBase64(key);
    }

}
