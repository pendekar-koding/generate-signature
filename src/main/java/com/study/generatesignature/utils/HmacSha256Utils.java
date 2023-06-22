package com.study.generatesignature.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class HmacSha256Utils {

    public static String sha256(PrivateKey privateKey, String stringToSign) throws InvalidKeyException, SignatureException, UnsupportedEncodingException, NoSuchAlgorithmException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(stringToSign.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signature.sign());
    }
}
