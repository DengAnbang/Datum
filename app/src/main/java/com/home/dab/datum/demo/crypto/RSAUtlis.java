package com.home.dab.datum.demo.crypto;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by DAB on 2016/12/28 10:28.
 */

public class RSAUtlis {
    private static volatile RSAUtlis sInstance;
    private static final Object key = new Object();

    public static RSAUtlis getInstance() {
        if (sInstance == null) {
            synchronized (key) {
                if (sInstance == null) {
                    sInstance = new RSAUtlis();
                }
            }
        }
        return sInstance;
    }


    /**
     * 加密算法RSA
     */
    private static final String KEY_ALGORITHM = "RSA";
    /**
     * 这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
     */
    private static final int KEY_SIZE = 1024;
    /**
     * java的使用的是RSA/ECB/PKCS1Padding
     * android使用的是RSA
     * 这里要和服务器统一
     */
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private KeyPair mKeyPair;

    private RSAUtlis() {
        generateKeyPair();
    }

    public RSAPublicKey getRSAPublicKey() {
        if (mKeyPair != null) {
            return (RSAPublicKey) mKeyPair.getPublic();
        } else {
            try {
                throw new KeyException("秘钥对为空!");
            } catch (KeyException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public RSAPrivateKey getRSAPrivateKey() {
        if (mKeyPair != null) {
            return (RSAPrivateKey) mKeyPair.getPrivate();
        } else {
            try {
                throw new KeyException("秘钥对为空!");
            } catch (KeyException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * 生成密钥对
     */
    private void generateKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());
            mKeyPair = keyPairGen.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密(用了base64编码)
     *
     * @param clearText
     * @return
     */
    public String encrypt(String clearText) {
        try {
            // 实例化加解密类
//            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            // 明文
            byte[] plainText = clearText.getBytes();
            // 加密
            cipher.init(Cipher.ENCRYPT_MODE, getRSAPublicKey());
            //将明文转化为根据公钥加密的密文，为byte数组格式
            byte[] enBytes = cipher.doFinal(plainText);
//            return new String(enBytes);
            return new String(Base64.encode(enBytes, Base64.DEFAULT));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解码(传入的密文需要base64编码)
     *
     * @param cipherText
     * @return
     */
    public String decode(String cipherText) {
        try {
//            Cipher cipher = Cipher.getInstance("RSA");
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, getRSAPrivateKey());
            //先将转为base64编码的加密后的数据转化为byte数组
//            byte[] enBytes = cipherText.getBytes();
            byte[] enBytes = Base64.decode(cipherText, Base64.DEFAULT);
            //解密称为byte数组，应该为字符串数组最后转化为字符串
            byte[] deBytes = cipher.doFinal(enBytes);
            return new String(deBytes);
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
