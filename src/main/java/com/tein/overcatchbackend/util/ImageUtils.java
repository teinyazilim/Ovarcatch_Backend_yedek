package com.tein.overcatchbackend.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class ImageUtils {

    public static byte[] resizeImage(MultipartFile file) throws  Exception {

        BufferedImage inputImage = ImageIO.read(file.getInputStream());
        //just resize if image width > 512
        if (inputImage.getWidth() > 512 ) {
            int newWidth = 512;
            double newHeight = (512.0/inputImage.getWidth())*inputImage.getHeight();
            Image resultingImage = inputImage.getScaledInstance(newWidth, (int)newHeight, Image.SCALE_SMOOTH );
            BufferedImage outputImage = new BufferedImage(newWidth, (int)newHeight, BufferedImage.TYPE_INT_RGB);
            outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(outputImage, "jpg", baos);
            return baos.toByteArray();
        }
        else {

            return file.getBytes();
        }





    }
}
