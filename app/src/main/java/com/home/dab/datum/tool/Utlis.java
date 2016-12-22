package com.home.dab.datum.tool;

import java.security.MessageDigest;

/**
 * Created by DAB on 2016/12/22 15:17.
 */

public class Utlis {
    /**
     * @param plainText
     *
     * @return md5值
     *
     * @author hewei
     */
    public static String getMD5Value(String plainText) {
        StringBuffer buffer = null;
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            buffer = new StringBuffer("");
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
        assert buffer != null;
        return buffer.toString();
    }
}
