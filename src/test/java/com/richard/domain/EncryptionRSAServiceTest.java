package com.richard.domain;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EncryptionRSAServiceTest implements WithAssertions {

    @Autowired
    private EncryptionRSAService encryptionRSAService;

    @Test
    @DisplayName("givenString_whenEncrypt_thenSuccess")
    void givenString_whenEncrypt_thenSuccess() throws Exception {
        // given
        String input = "richard";
        encryptionRSAService.createKeys();

        // when
        String encryptedMessgae = encryptionRSAService.encryptMessage(input);
        String response = encryptionRSAService.decryptMessage(encryptedMessgae);

        // then
        assertEquals(input, response);
    }


}