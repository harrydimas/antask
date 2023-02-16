package com.antask.util;

public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str) || str.length() == 0;
    }
}
