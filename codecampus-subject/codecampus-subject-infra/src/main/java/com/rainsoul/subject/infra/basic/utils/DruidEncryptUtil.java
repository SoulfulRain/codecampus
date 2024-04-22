package com.rainsoul.subject.infra.basic.utils;

import com.alibaba.druid.filter.config.ConfigTools;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class DruidEncryptUtil {

    private static String publicKey;

    private static String privateKey;

    /*这部分代码在类加载时执行。它尝试使用ConfigTools.genKeyPair(512)方法生成一个512位的密钥对，
    其中keyPair[0]是私钥，keyPair[1]是公钥。然后，它将这些密钥分别赋值给privateKey和publicKey静态变量。*/
    static {
        try {
            String[] keyPair = ConfigTools.genKeyPair(512);
            privateKey = keyPair[0];
            System.out.println("privateKey = " + privateKey);
            publicKey = keyPair[1];
            System.out.println("publicKey = " + publicKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对指定的明文进行加密。
     *
     * @param plainText 需要加密的明文字符串。
     * @return 加密后的字符串。
     * @throws Exception 如果加密过程中发生错误，则抛出异常。
     */
    public static String encrypt(String plainText) throws Exception {
        // 使用私钥对明文进行加密
        String encrypt = ConfigTools.encrypt(privateKey, plainText);
        System.out.println("encrypt:" + encrypt);
        return encrypt;
    }

    /**
     * 解密给定的加密文本。
     * @param encryptText 需要解密的文本。
     * @return 解密后的文本。
     * @throws Exception 如果解密过程中发生错误，则抛出异常。
     */
    public static String decrypt(String encryptText) throws Exception {
        // 使用公钥解密文本
        String decrypt = ConfigTools.decrypt(publicKey, encryptText);
        System.out.println("decrypt:" + decrypt);
        return decrypt;
    }

    public static void main(String[] args) throws Exception {
        /*String encrypt = encrypt("codecampus1120");
        System.out.println("encrypt:" + encrypt);*/
    }

}
