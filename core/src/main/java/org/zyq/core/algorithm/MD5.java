package org.zyq.core.algorithm;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public static String encode(String str) {
        try {
            return encode(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(byte[] bytes) {
        try {
            return toHash(MessageDigest.getInstance("MD5").digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toHash(byte[] bytes) {
        StringBuffer hex = new StringBuffer();
        for (byte b : bytes) {
            int val = ((int) b) & 0xff;
            if (val < 16) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(val));
        }
        return hex.toString();
    }

    public static String encode(File file) throws IOException {
        return encode(FileUtils.readFileToByteArray(file));
    }

    public static String encode(String salt, String str) {
        try {
            return encode(salt.getBytes("UTF-8"), str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encode(byte[] salt, byte[] str) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(salt);
            m.update(str);
            return toHash(m.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(MD5.encode("123456"));
    }
}
