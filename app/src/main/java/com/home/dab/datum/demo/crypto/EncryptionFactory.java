package com.home.dab.datum.demo.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by DAB on 2016/12/28 10:07.
 */

public class EncryptionFactory {

    static String getMD5Value(String plainText) {
        StringBuilder buffer = new StringBuilder("");
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buffer.append("0");
                buffer.append(Integer.toHexString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


    /**
     * SHA256算法
     *
     * @param strSrc
     * @return
     */
    public static String SHA256Encrypt(String strSrc) {
        if (strSrc == null) {
            return null;
        }
        MessageDigest md = null;
        String strDes = null;
        String encName;
        byte[] bt = strSrc.getBytes();
        try {
            encName = "SHA-256";
            md = MessageDigest.getInstance(encName);
            md.update(bt);
//            strDes = new String(md.digest());
            strDes = bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }



    public static String getRSAEncrypt(String plainText) {

        return RSAUtlis.getInstance().encrypt(plainText);
    }

    public static String getRSADecrypt(String plainText) {
        return RSAUtlis.getInstance().decode(plainText);
    }

    public static String getAESEncrypt(String plainText) throws Exception {
        byte[] encrypt = AESUtls.encrypt("1234567890123456", plainText);
        return new String(encrypt);
    }

    public static String getAESDecrypt(String plainText) throws Exception {
        byte[] decrypt = AESUtls.decrypt("1234567890123456", plainText);
        return new String(decrypt);
    }



}
