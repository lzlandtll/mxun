package com.mxun.common.utils;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/3/1
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description: 文本摘要工具类,主要用于摘要密码
 * @Author: liuzhilin
 * @Date: 2025/3/9 11:29
 */
public class TextDigester {

    /**
     * @Description: 摘要方法
     * @Author: liuzhilin
     * @Date: 2025/3/9 11:29
     */
    public static String digest(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String password = "123456";
        String hashedPassword = digest(password);
        System.out.println("Original Password: " + password);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
