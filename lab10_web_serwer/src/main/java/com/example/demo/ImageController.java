package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

@RestController
public class ImageController
{
    private BufferedImage modifyBrightness(BufferedImage originalImage, int brightness)
    {
        BufferedImage modifiedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());

        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                int rgb = originalImage.getRGB(x, y);
                int b = rgb & 0xFF;
                int g = (rgb & 0xFF00) >> 8;
                int r = (rgb & 0xFF0000) >> 16;

                b = clamp(b + brightness, 0, 255);  // niebieski
                g = clamp(g + brightness, 0, 255);  // zielony
                r = clamp(r + brightness, 0, 255);  // czerwony

                rgb = (r << 16) | (g << 8) | b;  // powrót bitów

                modifiedImage.setRGB(x, y, rgb);
            }
        }
        return modifiedImage;
    }

    private int clamp(int value, int min, int max)
    {
        return Math.max(min, Math.min(max, value));
    }
}
