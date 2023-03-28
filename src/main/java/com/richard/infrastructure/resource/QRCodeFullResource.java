package com.richard.infrastructure.resource;

import com.google.zxing.WriterException;
import com.richard.domain.QRCodeFullService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/full")
public class QRCodeFullResource {

    private final Logger LOG = LoggerFactory.getLogger(QRCodeFullResource.class);

    private final QRCodeFullService qrCodeFullService;

    public QRCodeFullResource(QRCodeFullService qrCodeFullService) {
        this.qrCodeFullService = qrCodeFullService;
    }

    @PostMapping("/generate/{number}")
    public ResponseEntity<String> generateQrCodeEncrytion(@PathVariable Long number) throws IOException, WriterException {
        return ResponseEntity.status(HttpStatus.CREATED).body(qrCodeFullService.generate(number));
    }

    @GetMapping("/{number}")
    public ResponseEntity<String> findQrCodeByNumber(@PathVariable Long number) throws Exception {
        LOG.info("{}.findQrCodeByNumber() - INIT ", this.getClass().getName());
        Optional<String> qrCodeByNumer = qrCodeFullService.findQrCodeByNumer(number);
        LOG.info("{}.findQrCodeByNumber() - END ", this.getClass().getName());
        return ResponseEntity.ok(qrCodeByNumer.orElse(""));
    }

}
