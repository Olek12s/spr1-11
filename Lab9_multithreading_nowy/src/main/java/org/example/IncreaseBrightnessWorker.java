package org.example;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Math.clamp;

public class IncreaseBrightnessWorker implements Runnable
{
    private int begin;
    private int end;
    private BufferedImage image;
    private int brightness;

    public IncreaseBrightnessWorker(int begin, int end, int brightness, BufferedImage image)
    {
        this.begin = begin;
        this.end = end;
        this.image = image;
        this.brightness = brightness;
    }

    // Histogram to tablica, w której każda komórka reprezentuje liczbę
    // wystąpień określonej wartości koloru w danym kanale (np. R, G, B).


    @Override
    public void run()
    {
        for (int y = begin; y < end; y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
                int rgb = image.getRGB(x, y);
                int b = rgb & 0xFF;
                int g = (rgb & 0xFF00) >> 8;
                int r = (rgb & 0xFF0000) >> 16;

                b = clamp(b + brightness, 0, 255);  // zakres od 0 do 255
                g = clamp(g + brightness, 0, 255);
                r = clamp(r + brightness, 0, 255);

                rgb = (r<<16)+(g<<8)+b;  // przesuwanie bitów z powrotem na swoje pozycje

                image.setRGB(x, y, rgb);
            }
        }
    }
}
