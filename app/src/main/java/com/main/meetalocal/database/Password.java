package com.main.meetalocal.database;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class for encrypting and decrypting passwords
 */
public class Password {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";

    public static String decrypt(String value) throws Exception {
        Key key = generateKey();

        @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte [] decryptedValue64 = Base64.decode(value, Base64.DEFAULT);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);

        return new String(decryptedByteValue, StandardCharsets.UTF_8);
    }

    public static String encrypt(String value) throws Exception {
        Key key = generateKey();

        @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte [] encryptedValue = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));

        return Base64.encodeToString(encryptedValue, Base64.DEFAULT);
    }

    private static Key generateKey() {
        return new SecretKeySpec(KEY.getBytes(), ALGORITHM);
    }
}
