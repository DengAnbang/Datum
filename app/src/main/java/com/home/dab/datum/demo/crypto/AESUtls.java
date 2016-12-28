package com.home.dab.datum.demo.crypto;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by DAB on 2016/12/28 14:21.
 * 密文都是经过base64编码过的
 */

public class AESUtls {
    private static final String KEY_ALGORITHM = "AES";

    public static byte[] encrypt(String secretKey, String plaintext) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(), KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return Base64.encode(cipher.doFinal(plaintext.getBytes()), Base64.DEFAULT);
    }

    public static byte[] encrypt(SecretKeySpec skeySpec, String plaintext) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
//        return cipher.doFinal(clear);
        return Base64.encode(cipher.doFinal(plaintext.getBytes()), Base64.DEFAULT);
    }

    public static byte[] decrypt(String secretKey, String cipherText) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(), KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] enBytes = Base64.decode(cipherText, Base64.DEFAULT);
        return cipher.doFinal(enBytes);
    }

    public static byte[] decrypt(SecretKeySpec skeySpec, String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] enBytes = Base64.decode(cipherText, Base64.DEFAULT);
        return cipher.doFinal(enBytes);
    }
}
