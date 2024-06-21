package org.example;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main
{
    public static void main(String[] args)
    {
        ImageProcessor processor = new ImageProcessor();
        processor.loadImage("obrazek.jpg"); // wczytywanie obrazu
        LocalDateTime start = LocalDateTime.now();
        processor.increaseBrightness(25);
        LocalDateTime end = LocalDateTime.now();

        Duration deltaTime = Duration.between(start, end);

        System.out.println("Czas dla jednego wątku: " + deltaTime.toMillis() + " ms");

        start = LocalDateTime.now();
        processor.increaseBrightnessWithThreads(25);
        end = LocalDateTime.now();

        deltaTime = Duration.between(start, end);

        System.out.println("Czas dla wielu wątku: " + deltaTime.toMillis() + " ms");

        processor.saveImage("src/images/obrazek.jpg");  // zapisywanie obrazu

        //RANDOMOWY HISTOGRAM:
        int[] histogramRand = new int[256];
        for (int i = 0; i < 256; i++) {
            histogramRand[i] = (int) (Math.random() * 500); // Losowe wartości do histogramu
        }


        processor.generateHistogramImg(histogramRand, "src/images/histogram.png");

        //HISTOGRAM Z OBRAZKA:

        int[] histogramImgR = processor.computeHistogramWithThreads(0); // // 0Red 1Green 2Blue
        processor.generateHistogramImg(histogramImgR, "src/images/histogram_imageR.png");

        int[] histogramImgG = processor.computeHistogramWithThreads(1); // // 0Red 1Green 2Blue
        processor.generateHistogramImg(histogramImgG, "src/images/histogram_imageG.png");

        int[] histogramImgB = processor.computeHistogramWithThreads(2); // // 0Red 1Green 2Blue
        processor.generateHistogramImg(histogramImgB, "src/images/histogram_imageB.png");


    }
}