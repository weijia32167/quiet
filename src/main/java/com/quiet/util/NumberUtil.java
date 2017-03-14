package com.quiet.util;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2017/2/15
 * Desc   :
 */
public final class NumberUtil {

    /*判断N是否为2的幂次方*/
    public static boolean isTwoPow(int n) {
        return (n & (n - 1)) == 0;
    }


    public static String byte2HexString(byte num) {
        StringBuffer result = new StringBuffer();
        String temp = Integer.toHexString(num & 0xff);
        if (temp.length() == 1) {
            result.append("0").append(temp);
        } else {
            result.append(temp);
        }
        return result.toString();
    }

    public static byte[] long2Bytes(long num) {
        byte[] result = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            result[ix] = (byte) ((num >> offset) & 0xff);
        }
        return result;
    }

    public static byte[] int2Bytes(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

}
