package com.richard.infrastructure.resource;


import com.richard.domain.EncryptionRSAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rsa")
public class RSAResource {

    private final Logger LOG = LoggerFactory.getLogger(RSAResource.class);

    private final EncryptionRSAService encryptionRSAService;

    public RSAResource(EncryptionRSAService encryptionRSAService) {
        this.encryptionRSAService = encryptionRSAService;
    }

    @GetMapping("/createKeys")
    public void createPrivatePublickey() {
        LOG.info("{}.createPrivatePublickey() - INIT ", this.getClass().getName());
        encryptionRSAService.createKeys();
        LOG.info("{}.createPrivatePublickey() - END ", this.getClass().getName());
    }

    @PostMapping("/encrypt")
    public ResponseEntity<String> encryptMessage(@RequestBody String request) {
        LOG.info("{}.encryptMessage() - INIT request: {}", this.getClass().getName(), request);
        var response = encryptionRSAService.encryptMessage(request);
        LOG.info("{}.encryptMessage() - END request: {}", this.getClass().getName(), request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dencrypt")
    public ResponseEntity<String> dencryptMessage(@RequestBody String request) {
        LOG.info("{}.dencryptMessage() - INIT request: {}", this.getClass().getName(), request);
        var response = encryptionRSAService.decryptMessage(request);
        LOG.info("{}.dencryptMessage() - END request: {}", this.getClass().getName(), request);
        return ResponseEntity.ok(response);
    }

}
