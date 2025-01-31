package com.github.chileh.utils;

import java.util.Base64;

public class Base64Utils {

    /**
     * 解码
     * @param   base64 Base64 编码的字符串
     * @return  解码后的字节数组
     * @throws IllegalArgumentException 如果解码失败
     */
    public static byte[] decode(String base64){
        try {
            // Base64解码
            return Base64.getDecoder().decode(base64);
        } catch (Exception e) {
            throw new IllegalArgumentException("Base64 decode failed", e);
        }
    }


    /**
     * 编码
     * @param data 要编码的字节数组
     * @return Base64 编码后的字符串
     */
    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
