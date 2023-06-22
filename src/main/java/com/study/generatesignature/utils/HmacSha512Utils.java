package com.study.generatesignature.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HmacSha512Utils {

    public static String calculateHmacSha512(String message, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha512Hmac = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA512");
        sha512Hmac.init(secretKey);
        byte[] hmacData = sha512Hmac.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(hmacData);
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
