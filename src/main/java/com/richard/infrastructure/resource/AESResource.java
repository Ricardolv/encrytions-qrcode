package com.richard.infrastructure.resource;


import com.richard.domain.EncryptionEASService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aes")
public class AESResource {

    private final Logger LOG = LoggerFactory.getLogger(AESResource.class);

    private final EncryptionEASService encryptionEASService;

    public AESResource(EncryptionEASService encryptionEASService) {
        this.encryptionEASService = encryptionEASService;
    }

    @GetMapping("/createKeys")
    public void createPrivatePublickey() throws Exception {
        LOG.info("{}.createPrivatePublickey() - INIT ", this.getClass().getName());
        encryptionEASService.createKeys(128);
        LOG.info("{}.createPrivatePublickey() - END ", this.getClass().getName());
    }

    @PostMapping("/encrypt")
    public ResponseEntity<String> encryptMessage(@RequestBody String request) throws Exception {
        LOG.info("{}.encryptMessage() - INIT request: {}", this.getClass().getName(), request);
        var response = encryptionEASService.encrypt(request);
        LOG.info("{}.encryptMessage() - END request: {}", this.getClass().getName(), request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dencrypt")
    public ResponseEntity<String> dencryptMessage(@RequestBody String request) throws Exception {
        LOG.info("{}.dencryptMessage() - INIT request: {}", this.getClass().getName(), request);
        var response = encryptionEASService.decrypt(request);
        LOG.info("{}.dencryptMessage() - END request: {}", this.getClass().getName(), request);
        return ResponseEntity.ok(response);
    }

}
