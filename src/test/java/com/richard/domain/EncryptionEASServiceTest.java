package com.richard.domain;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EncryptionEASServiceTest implements WithAssertions {

   @Autowired
   private EncryptionEASService encryptionEASService;
    @Test
    @DisplayName("givenString_whenEncrypt_thenSuccess")
    void givenString_whenEncrypt_thenSuccess() throws Exception {
        // given
        String input = "richard";
        encryptionEASService.createKeys(128);

        // when
        String cipherText = encryptionEASService.encrypt(input);
        String plainText = encryptionEASService.decrypt(cipherText);

        // then
        assertEquals(input, plainText);
    }

    @Test
    @DisplayName("givenObject_whenEncrypt_thenSuccess")
    void givenObject_whenEncrypt_thenSuccess() throws Exception {
        // given
        Student student = new Student("Richard", 20);
        SecretKey key = encryptionEASService.createKeys(128);
        IvParameterSpec ivParameterSpec = encryptionEASService.generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";

        // when
        SealedObject sealedObject = encryptionEASService.encryptObject(algorithm, student, key, ivParameterSpec);
        Student object = (Student) encryptionEASService.decryptObject(algorithm, sealedObject, key, ivParameterSpec);

        // then
        assertThat(student).isEqualTo(object);
    }

}