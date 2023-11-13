package edu.xmut.examsys.utils;

import edu.xmut.examsys.constants.SystemConstant;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 朔风
 * @date 2023-11-13 11:04
 */
public class MD5Utils {
    /**
     * 使用MD5将密码加密成Hex字符串
     *
     * @param password 密码
     * @return 返回加密后的Hex密码
     */
    public static String toMD5(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update((password + SystemConstant.SALT).getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        return byteArrayToHexString(digest);
    }

    /**
     * 字节数组转化为十六进制字符串
     * @param digest 摘要内容
     */
    private static String byteArrayToHexString(byte[] digest) {
        int i = 0;
        StringBuilder buf = new StringBuilder();
        for (byte b : digest) {
            i = b;
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }


}
