package com.mxun.common.utils;

import java.util.Objects;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/1
 */
public class StringUtils {
    public static boolean equalAny(String value, String... candidates) {
        for (String candidate : candidates) {
            if (Objects.equals(value, candidate)) {
                return true;
            }
        }
        return false;
    }
}
