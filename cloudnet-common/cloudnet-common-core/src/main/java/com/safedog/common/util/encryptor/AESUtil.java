package com.safedog.common.util.encryptor;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    private static final String initVector = "encryptionIntVec";
    //密文，由调用时传入,或者初始化时使用
    private final String KEY = "";
    private static final String chart_encoding = CharEncoding.UTF_8;
    private static final String ALGORITHMSTR = "AES/CBC/PKCS5PADDING";
    /**
     * 加密
     * @param value
     * @param key
     * @return
     */
    public static String encrypt(String value, String key) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(chart_encoding));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(chart_encoding), "AES");

            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param encrypted
     * @param key
     * @return
     */
    public static String decrypt(String encrypted, String key) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(chart_encoding));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(chart_encoding), "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String encrypt = AESUtil.encrypt( "1", "safedogcloudeyes");
        System.out.println(encrypt);
        System.out.println("====================");
        String decrypt = AESUtil.decrypt(encrypt, "safedogcloudeyes");
        System.out.println(decrypt);
    }
}
