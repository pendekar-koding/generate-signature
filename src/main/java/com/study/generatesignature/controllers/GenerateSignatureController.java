package com.study.generatesignature.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.generatesignature.utils.HmacSha256Utils;
import com.study.generatesignature.utils.HmacSha512Utils;
import com.study.generatesignature.utils.Sha256Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/generateSignature")
public class GenerateSignatureController {
    private static final Logger logger = LogManager.getLogger(GenerateSignatureController.class.getName());

    @PostMapping(value = "/request-token")
    public Map<String, Object> requestToken(@RequestParam String time, @RequestParam String client, @RequestParam String privateKey) throws IOException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        Map<String, Object> map = new HashMap<>();
        PrivateKey newPrivateKey = generatePrivateKey(privateKey.replace(" ", ""));
        logger.info(newPrivateKey);
        String stringToSign = client + "|" + time;
        String signature = HmacSha256Utils.sha256(newPrivateKey, stringToSign);
        map.put("STATUS-CODE", 200);
        map.put("STATUS", true);
        map.put("X-SIGNATURE", signature);
        return map;
    }

    @PostMapping(value = "/inquiry")
    public Map<String, Object> inquiry(@RequestBody Object wrapper,
                                       @RequestParam String token,
                                       @RequestParam String time,
                                       @RequestParam String secretKey) throws NoSuchAlgorithmException, JsonProcessingException, InvalidKeyException {
        Map<String, Object> map = new HashMap<>();

        String hexHash = Sha256Utils.hexSha256(wrapper);
        String requestToken = token.toLowerCase().replace("Bearer ", "");

        String stringToSign = HttpMethod.POST + ":/openapi/v1.0/transfer-va/inquiry:" + requestToken + ":"
                + hexHash + ":" + time;

        String signature = HmacSha512Utils.calculateHmacSha512(stringToSign, secretKey);
        map.put("STATUS-CODE", 200);
        map.put("STATUS", true);
        map.put("X-SIGNATURE", signature);
        return map;
    }

    @PostMapping(value = "/payment")
    public Map<String, Object> payment(@RequestBody Object wrapper,
                                       @RequestParam String token,
                                       @RequestParam String time,
                                       @RequestParam String secretKey) throws NoSuchAlgorithmException, JsonProcessingException, InvalidKeyException {
        Map<String, Object> map = new HashMap<>();

        String hexHash = Sha256Utils.hexSha256(wrapper);
        String requestToken = token.toLowerCase().replace("Bearer ", "");

        String stringToSign = HttpMethod.POST + ":/openapi/v1.0/transfer-va/payment:" + requestToken + ":"
                + hexHash + ":" + time;

        String signature = HmacSha512Utils.calculateHmacSha512(stringToSign, secretKey);
        map.put("STATUS-CODE", 200);
        map.put("STATUS", true);
        map.put("X-SIGNATURE", signature);
        return map;
    }


    private PrivateKey generatePrivateKey(String value) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(value);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
