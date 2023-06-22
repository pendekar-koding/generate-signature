package com.study.generatesignature.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Utils {
    public static String hexSha256(Object wrapper) throws JsonProcessingException, NoSuchAlgorithmException {
        ObjectMapper mapper = new ObjectMapper();
        String requestBodyJson = mapper.writeValueAsString(wrapper);
        System.out.println(requestBodyJson);
        String minifiedRequestBodyJson = mapper.writeValueAsString(mapper.readTree(requestBodyJson));
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(minifiedRequestBodyJson.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printHexBinary(hashBytes).toLowerCase();
    }

}
