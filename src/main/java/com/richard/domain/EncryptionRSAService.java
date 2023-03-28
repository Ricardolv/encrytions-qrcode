package com.richard.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Service
public class EncryptionRSAService {
    private final Logger LOG = LoggerFactory.getLogger(EncryptionRSAService.class);

    public static Map<String, Object> map = new HashMap<>();

    public void createKeys() {
        LOG.info("{}.createKeys() - INIT ", this.getClass().getName());

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(4096);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            map.put("publicKey", publicKey);
            map.put("privateKey", privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOG.info("{}.createKeys() - END ", this.getClass().getName());
    }

    public String encryptMessage(String plainText) {
        LOG.info("{}.encryptMessage() - INIT ", this.getClass().getName());

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
            PublicKey publicKey = (PublicKey) map.get("publicKey");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypt = cipher.doFinal(plainText.getBytes());

            LOG.info("{}.encryptMessage() - END ", this.getClass().getName());
            return new String(Base64.getEncoder().encodeToString(encrypt));
        } catch (Exception e) {
            LOG.info("{}.encryptMessage() - ERROR {}", this.getClass().getName(), e.getMessage());
        }

        LOG.info("{}.encryptMessage() - END ", this.getClass().getName());
        return "";
    }

    public String decryptMessage(String encryptedMessgae) {
        LOG.info("{}.decryptMessage() - INIT ", this.getClass().getName());

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
            PrivateKey privateKey = (PrivateKey) map.get("privateKey");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decrypt = cipher.doFinal(Base64.getDecoder().decode(encryptedMessgae));
            return new String(decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOG.info("{}.decryptMessage() - END ", this.getClass().getName());
        return "";
    }

}
