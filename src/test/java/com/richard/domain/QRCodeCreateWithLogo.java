package com.richard.domain;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;

public class QRCodeCreateWithLogo {

    public BufferedImage gengrateQRCode(String contents, int size){
        BufferedImage targetImage=null;

        EnumMap<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.QR_VERSION, 2);

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, size, size, hints);
            targetImage = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
            for(int x=0;x<size;x++){
                for(int y=0;y<size;y++){
                    targetImage.setRGB(x, y, bitMatrix.get(x, y)? 0x00000000:0xFFFFFFFF);// 0x0ac516 green QR code
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetImage;
    }
    // srcqrcode is the QR code to add logo, path is logo path
    public BufferedImage insertLogo(BufferedImage srcQRCode, String path, int size) throws Exception {
        File logo = new File(path);

        if (!logo.exists()) {
            return srcQRCode;
        }

        Image logoImg = ImageIO.read(logo);

        Image targetLogo = logoImg.getScaledInstance(size, size, Image.SCALE_SMOOTH); // Compressed LOGO
        BufferedImage targetBuffLogo = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        targetBuffLogo.getGraphics().drawImage(targetLogo, 0, 0,null); // Regenerate the compressed logo

        int pos = (srcQRCode.getWidth() - size) / 2;
        Graphics2D graphics = srcQRCode.createGraphics();

        graphics.drawImage(srcQRCode, 0, 0, null);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        graphics.drawImage(targetBuffLogo, pos, pos, null); // Draw logo

        Shape shape = new RoundRectangle2D.Float(pos, pos, size, size, 6, 6); // Draw a border with rounded corners
        graphics.setStroke(new BasicStroke(1F)); // Set the brush (border) width
        graphics.draw(shape);
        graphics.dispose();

        return srcQRCode;
    }

    public void saveImage(BufferedImage targetImage, String format, String path) {
        try {
            ImageIO.write(targetImage, format, new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void testGenerate() throws WriterException, IOException {

        EnumMap<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.QR_VERSION, 2);

        BitMatrix bitMatrix = new MultiFormatWriter().encode("https://google.com", BarcodeFormat.QR_CODE, 256, 256, hints);
        BufferedImage srcQRCode = new BufferedImage(256,256, BufferedImage.TYPE_INT_RGB);

        for(int x=0;x<256;x++){
            for(int y=0;y<256;y++){
                srcQRCode.setRGB(x, y, bitMatrix.get(x, y)? 0x00000000:0xFFFFFFFF);
            }
        }

        // Getting logo image
        BufferedImage logoImage = ImageIO.read(new File("/home/richard/git/encrytions-qrcode/src/main/resources/images/logo.png"));
        Image targetLogo = logoImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        //Merging both images
        BufferedImage targetBuffLogo = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        targetBuffLogo.getGraphics().drawImage(targetLogo, 0, 0,null);

        int pos = (srcQRCode.getWidth() - 40) / 2;

        srcQRCode.createGraphics();
        Graphics2D graphics = (Graphics2D) srcQRCode.getGraphics();

        graphics.drawImage(srcQRCode, 0, 0, null);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        graphics.setBackground(Color.WHITE);
        graphics.clearRect(pos, pos, 40, 40);

        graphics.drawImage(targetBuffLogo, pos, pos, null);

        Shape shape = new RoundRectangle2D.Float(pos, pos, 40, 40, 6, 6); // Draw a border with rounded corners
        graphics.setStroke(new BasicStroke(0F)); // Set the brush (border) width
        graphics.draw(shape);
        graphics.dispose();

        ImageIO.write(srcQRCode, "png", new File("/home/richard/git/encrytions-qrcode/src/main/resources/images/writer.png"));
    }


    public static void main(String [] args) throws Exception {
        QRCodeCreateWithLogo qrcodeTool = new QRCodeCreateWithLogo();
//        BufferedImage qrCode = qrcodeTool.gengrateQRCode("https://rd.com.br", 256);
//
//        BufferedImage qrCodeWidLogo=qrcodeTool.insertLogo(qrCode, "/home/richard/git/encrytions-qrcode/src/main/resources/images/logo.png", 40);
//        qrcodeTool.saveImage(qrCodeWidLogo, "png", "/home/richard/git/encrytions-qrcode/src/main/resources/images/writer.png");

        qrcodeTool.testGenerate();
    }

}
