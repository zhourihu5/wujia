package com.abctime.lib_common.utils;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.abctime.native_lib.NativeLib;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypt {

    private final static String CODE_MODE = "AES";
    private final static String CIPHERMODEPADDING = "AES/CBC/PKCS7Padding";

    public static final String APP_PASSWORD_KEY = "abctime";


    private final static byte[] iv = {0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3,
            1, 6, 8, 0xC, 0xD, 91};

    private static final IvParameterSpec IV = new IvParameterSpec(iv);
    ;

    public static byte[] md5GeneralKey(byte[] password) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(password);

            return md5.digest();
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    private static SecretKey generalSeed(byte[] password) {
        SecretKeySpec sk = null;
        byte[] key = md5GeneralKey(password);
        if (key != null) {
            sk = new SecretKeySpec(key, CODE_MODE);
        }
        return sk;
    }

    public static byte[] bytesEncrypt(byte[] plaintext, String password) {
        SecretKey sk = generalSeed(password.getBytes());

        return encrypt(CIPHERMODEPADDING, sk, IV, plaintext);
    }

    public static byte[] bytesDecrypt(byte[] plaintext, int offset, int length,
                                      String password) {
        SecretKey sk = generalSeed(password.getBytes());
        return decrypt(CIPHERMODEPADDING, sk, IV, plaintext, offset, length);
    }

    public static byte[] bytesDecrypt(byte[] plaintext, String password) {
        return bytesDecrypt(plaintext, 0, plaintext.length, password);
    }

    private static byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV,
                                  byte[] msg) {
        try {
            Cipher c = Cipher.getInstance(cmp);
            c.init(Cipher.ENCRYPT_MODE, sk, IV);
            return c.doFinal(msg);
        } catch (NoSuchAlgorithmException nsae) {
        } catch (NoSuchPaddingException nspe) {
        } catch (InvalidKeyException e) {
        } catch (InvalidAlgorithmParameterException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (BadPaddingException e) {
        }
        return null;
    }

    private static byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV,
                                  byte[] ciphertext, int offset, int length) {
        try {
            Cipher c = Cipher.getInstance(cmp);
            c.init(Cipher.DECRYPT_MODE, sk, IV);
            return c.doFinal(ciphertext, offset, length);
        } catch (NoSuchAlgorithmException nsae) {
        } catch (NoSuchPaddingException nspe) {
        } catch (InvalidKeyException e) {
        } catch (InvalidAlgorithmParameterException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (BadPaddingException e) {
        }
        return null;
    }

    /**
     * 加密
     *
     * @param plaintext 要加密的字符串
     * @param password  加密的key
     * @return 加密后的String
     */
    public static String encrypt(String plaintext, String password) {
        return encrypt(plaintext.getBytes(), password);
    }

    /**
     * 加密
     *
     * @param plaintext 要加密的数组
     * @param password  加密的key
     * @return 加密后的String
     */
    public static String encrypt(byte[] plaintext, String password) {
        byte[] ciphertext = bytesEncrypt(plaintext, password);
        byte[] base64_ciphertext = Base64.encode(ciphertext,
                android.util.Base64.DEFAULT);

        String decrypted = new String(base64_ciphertext);
        return decrypted;
    }

    /**
     * 解密
     *
     * @param ciphertext_base64 要解密的字符串
     * @param password          解密的key
     * @return 解密的结果
     */
    public static String decrypt(String ciphertext_base64, String password) {
        try {
            if (TextUtils.isEmpty(ciphertext_base64)) {
                return "";
            } else {
                byte[] s = Base64.decode(ciphertext_base64, android.util.Base64.DEFAULT);
                String decrypted = new String(bytesDecrypt(s, password));
                return decrypted;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String decrypt(String ciphertext_base64) {
        try {
            if (TextUtils.isEmpty(ciphertext_base64)) {
                return "";
            } else {
                byte[] s = Base64.decode(ciphertext_base64, android.util.Base64.DEFAULT);
                String password = makeSecrect();
                if (TextUtils.isEmpty(password)) {
                    return "";
                }
                String decrypted = new String(bytesDecrypt(s, password));
                return decrypted;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String makeSecrect() {
        String key = NativeLib.getSecretKey(AppContext.get());
        LogUtil.d(key);
        if (TextUtils.isEmpty(key)) {
            return "";
        }
        byte[] bytes = md5GeneralKey(key.getBytes());
        if (bytes == null)
            return key;
        return new String(bytes);
    }

}