package com.richard.domain;

import com.google.zxing.WriterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class QRCodeFullService {
    private final Logger LOG = LoggerFactory.getLogger(QRCodeFullService.class);
    private final EncryptionRSAService encryptionRSAService;
    private final QRCodeService qrCodeService;
    private static Map<Long, String> qRCodeMap = new HashMap<>();

    public QRCodeFullService(EncryptionRSAService encryptionRSAService, QRCodeService qrCodeService) {
        this.encryptionRSAService = encryptionRSAService;
        this.qrCodeService = qrCodeService;
    }

    public String generate(final Long number) throws IOException, WriterException {
        LOG.info("{}.generate() - INIT ", this.getClass().getName());

        encryptionRSAService.createKeys();
        String encryptMessage = encryptionRSAService.encryptMessage(number.toString());
        String generateQRCode = qrCodeService.generateQRCode(encryptMessage, 250, 250);

        qRCodeMap.put(number, generateQRCode);

        LOG.info("{}.generate() - END ", this.getClass().getName());
        return generateQRCode;
    }

    public Optional<String> findQrCodeByNumer(Long number) {
        return ofNullable(qRCodeMap.get(number));
    }

}
