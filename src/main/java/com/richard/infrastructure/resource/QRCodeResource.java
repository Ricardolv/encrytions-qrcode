package com.richard.infrastructure.resource;

import com.google.zxing.WriterException;
import com.richard.domain.QRCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/qrCode")
public class QRCodeResource {

    private final QRCodeService qrCodeService;

    public QRCodeResource(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateQRCode(@RequestBody String request) throws IOException, WriterException {
        String qrCode = qrCodeService.generateQRCode(request, 250, 250);
        return ResponseEntity.ok(qrCode);
    }

    @PostMapping("/read")
    public ResponseEntity<String> readQRCode(@RequestBody String request) throws IOException, WriterException {
        String qrCode = qrCodeService.readQRCode(request);
        return ResponseEntity.ok(qrCode);
    }
}
