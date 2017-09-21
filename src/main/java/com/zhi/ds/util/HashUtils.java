package com.zhi.ds.util;

/**
 * @author zhimeng
 *         email: zhimeng@douyu.tv
 *         weichat: mengzhi825
 *         date: 2017/9/21.
 *         <p>
 *         FNV1_32_HASH
 */
public class HashUtils {

    public static int hash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }
}
