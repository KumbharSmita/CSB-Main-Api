package com.example.accountapi.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int SESSION_KEY_SIZE = 32; // 32 bytes
    private static final int IV_SIZE = 16;          // 16 bytes

    // Generate a random 32-byte session key
    public static byte[] generateSessionKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // 256-bit key size
        SecretKey secretKey = keyGen.generateKey();
        return secretKey.getEncoded();
    }

    // Generate a random 16-byte IV
    public static byte[] generateIv() {
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    // Encrypt the payload
    public static String encrypt(String plainText, byte[] sessionKey) throws Exception {
        byte[] iv = generateIv(); // Generate IV
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec keySpec = new SecretKeySpec(sessionKey, "AES");

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encryptedData = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        // Concatenate IV and Encrypted Data
        byte[] cipherData = new byte[IV_SIZE + encryptedData.length];
        System.arraycopy(iv, 0, cipherData, 0, IV_SIZE);
        System.arraycopy(encryptedData, 0, cipherData, IV_SIZE, encryptedData.length);

        // Return as Base64 encoded string
        return Base64.getEncoder().encodeToString(cipherData);
    }

    // Decrypt the payload
    public static String decrypt(String encryptedText, byte[] sessionKey) throws Exception {
        byte[] cipherData = Base64.getDecoder().decode(encryptedText);

        // Extract IV (first 16 bytes)
        byte[] iv = new byte[IV_SIZE];
        System.arraycopy(cipherData, 0, iv, 0, IV_SIZE);

        // Extract Encrypted Data
        byte[] encryptedData = new byte[cipherData.length - IV_SIZE];
        System.arraycopy(cipherData, IV_SIZE, encryptedData, 0, encryptedData.length);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec keySpec = new SecretKeySpec(sessionKey, "AES");

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decryptedData = cipher.doFinal(encryptedData);

        // Convert to String and return
        return new String(decryptedData, StandardCharsets.UTF_8);
    }
}
