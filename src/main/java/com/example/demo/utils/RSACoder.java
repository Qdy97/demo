package com.example.demo.utils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author QDY
 * @date 2020/11/18
 */
public class RSACoder {

    public final static String CONFIG_KEY = "RSA";

    /**
     * 生成公钥字符串
     * @param seed 随机数种子
     * @return 返回一个公钥字符串
     * @throws NoSuchAlgorithmException 生成随机数时可能返回该异常
     */
    public static String genPublicKeyPair(byte[] seed) throws NoSuchAlgorithmException {
        //KeyPairGenerator用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator rsa = null;
        try {
            rsa = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //初始化密钥对生成器
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(seed);
        rsa.initialize(1024,random);
        //将密钥对保存在keypair中
        KeyPair keyPair = rsa.generateKeyPair();
        //得到公钥
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        return Coder.encryptBASE64(rsaPublicKey.getEncoded());
    }

    /**
     * 生成私钥字符串
     * @param seed 随机数种子
     * @return 返回一个私钥字符串
     * @throws NoSuchAlgorithmException 生成随机数时可能返回该异常
     */
    public static String genPrivateKeyPair(byte[] seed) throws NoSuchAlgorithmException {
        //KeyPairGenerator用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator rsa = null;
        try {
            rsa = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //初始化密钥对生成器
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(seed);
        rsa.initialize(1024,random);
        //将密钥对保存在keypair中
        KeyPair keyPair = rsa.generateKeyPair();
        //得到私钥
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        return Coder.encryptBASE64(rsaPrivateKey.getEncoded());
    }

    /**
     * 通过公钥字符串加密数据
     * @param publicKey 公钥字符串
     * @param data 未加密数据
     * @return 公钥加密后数据
     */
    public static byte[] publicEncrypt(String publicKey,byte[] data) throws Exception {
        if(publicKey == null){
            throw new Exception("公钥为空，请检查传入参数");
        }
        //将公钥字符串解析为公钥
        RSAPublicKey rsaPublicKey = stringToPublicKey(publicKey);
        //使用公钥生成主要加密工具类
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,rsaPublicKey);
        //返回加密后的数据
        return cipher.doFinal(data);
    }
    /**
     * 通过私钥字符串加密数据
     * @param privateKey 私钥字符串
     * @param data 未加密数据
     * @return 私钥加密后数据
     */
    public static byte[] privateEncrypt(String privateKey,byte[] data) throws Exception {
        if(privateKey == null){
            throw new Exception("私钥为空，请检查传入参数");
        }
        //将私钥字符串解析为私钥
        RSAPrivateKey rsaPrivateKey = stringToPrivateKey(privateKey);
        //使用私钥生成主要加密工具类
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,rsaPrivateKey);
        //返回加密后的数据
        return cipher.doFinal(data);
    }

    /**
     * 通过公钥字符串解密数据
     * @param publicKey 公钥字符串
     * @param data 已加密数据数组
     * @return 返回解密后数据数组
     * @throws Exception 抛出异常
     */
    public static byte[] publicDecrypt(String publicKey,byte[] data) throws Exception {
        if (publicKey == null){
            throw new Exception("公钥为空，请检查传入参数");
        }
        //将公钥字符串解析为公钥实体类
        RSAPublicKey rsaPublicKey = stringToPublicKey(publicKey);
        //使用公钥生成主要解密工具类
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,rsaPublicKey);
        //返回解密后的数据
        return cipher.doFinal(data);
    }

    /**
     * 通过私钥字符串解密数据
     * @param privateKey 私钥字符串
     * @param data 已加密数据数组
     * @return 返回解密后数据数组
     * @throws Exception 抛出异常
     */
    public static byte[] privateDecrypt(String privateKey,byte[] data) throws Exception {
        if (privateKey == null){
            throw new Exception("私钥为空，请检查传入参数");
        }
        //将私钥字符串解析为私钥实体类
        RSAPrivateKey rsaPrivateKey = stringToPrivateKey(privateKey);
        //使用公钥生成主要解密工具类
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,rsaPrivateKey);
        //返回解密后的数据
        return cipher.doFinal(data);
    }

    /**
     * 将传入的私钥字符串转换为私钥实体类
     * @param privateKey 私钥字符串
     * @return 私钥
     * @throws NoSuchAlgorithmException 生成随机数时可能会发生的异常
     * @throws InvalidKeySpecException 获取私钥实体类时如果加密时格式不一致会抛出该异常
     */
    private static RSAPrivateKey stringToPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] kryBytes = Coder.decodeBASE64(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(kryBytes);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    /**
     * 将传入的公钥字符串转换为公钥实体类
     * @param publicKey 公钥字符串
     * @return 公钥
     * @throws NoSuchAlgorithmException 生成随机数时可能会发生的异常
     * @throws InvalidKeySpecException 获取公钥实体类时如果加密时格式不一致会抛出该异常
     */
    private static RSAPublicKey stringToPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] kryBytes = Coder.decodeBASE64(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(kryBytes);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    /**
     * 主方法测试公钥加密私钥解密使用
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String random = "12345678901234567890";
        //生成公钥
        String publicKey = genPublicKeyPair(Coder.decodeBASE64(RSACoder.CONFIG_KEY));
        //生成私钥
        String privateKey = genPrivateKeyPair(Coder.decodeBASE64(RSACoder.CONFIG_KEY));
        //通过公钥进行加密
        byte[] encryptData = publicEncrypt(publicKey, random.getBytes());
        //将加密数据编码为字符串
        String data = Coder.encryptBASE64(encryptData);
        System.out.println(data);
        //通过私钥对数据进行解密
        byte[] decodeBytes = Coder.decodeBASE64(data);
        byte[] decryptBytes = privateDecrypt(privateKey, decodeBytes);
        System.out.println(new String(decryptBytes));

    }
}
