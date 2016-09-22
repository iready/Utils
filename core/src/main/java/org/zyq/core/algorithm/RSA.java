package org.zyq.core.algorithm;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by zouyq on 2016/4/9.
 */
public class RSA {


    public static String encode(String publicKey, String content) {
        try {
            Cipher cipher = getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            return Base64.encode(cipher.doFinal(content.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static PublicKey getPublicKey(String key) {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decodeBuffer(key)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Cipher getCipher() {
        try {
            return Cipher.getInstance("RSA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}