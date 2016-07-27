package org.zyq.core.algorithm;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Base64 {
    public static String encode(String str) {
        try {
            new String(str.getBytes(), "utf-8");
            return new BASE64Encoder().encode(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(byte[] b) {
        return new BASE64Encoder().encode(b);
    }

    public static byte[] decodeBuffer(String b) {
        try {
            return new BASE64Decoder().decodeBuffer(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decode(String str) {
        try {
            return new String(new BASE64Decoder().decodeBuffer(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
