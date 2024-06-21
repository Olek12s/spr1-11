package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.clamp;

public class ImageProcessor
{
    private BufferedImage image;

    public void loadImage(String path)  // wczytywanie obrazu
    {
        File file = new File(path);
        try {
            this.image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveImage(String path)
    {
        File fileToSave = new File(path);
        try {
            ImageIO.write(image, "jpg", fileToSave);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void increaseBrightness(int brightness)
    {
        for (int x = 0; x < image.getWidth(); x++)
        {
            for (int y = 0; y < image.getHeight(); y++)
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

    public void increaseBrightnessWithThreads(int brightness)
    {
        int threadsCount = Runtime.getRuntime().availableProcessors();
        Thread[] threads;
        threads = new Thread[threadsCount];
        int chunk = image.getHeight() / threadsCount;

        for (int i = 0; i < threadsCount; i++)
        {
            int begin = i*chunk;
            int end;

            if (i == threadsCount - 1)
            {
                end = image.getHeight();
            }
            else
            {
                end = (i+1) * chunk;
            }
            threads[i] = new Thread(new IncreaseBrightnessWorker(begin, end, brightness, image));
            threads[i].start();
        }
        for (int i = 0; i < threadsCount; i++)
        {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int[] computeHistogramWithThreads(int channel)   // 0Red 1Green 2Blue
    {
        if (image == null)
        {
            System.out.println("Brak wczytanego obrazu.");
            return null;
        }
        int[] histogram = new int[256];
        int width = image.getWidth();
        int height = image.getHeight();
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        int chunk = image.getHeight() / numThreads;

        for (int i = 0; i < numThreads; i++)
        {
            int startY = i * chunk;
            int endY;
            if (i == numThreads - 1)
            {
                endY = image.getHeight();
            }
            else
            {
                endY = (i + 1) * chunk;
            }
            Runnable task = new HistogramCalculationWorker(histogram, channel, startY, endY, image);
            executor.execute(task);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Wątek został przerwany: " + e.getMessage());
        }

        return histogram;
    }

    public void generateHistogramImg(int[] histogram, String path)
    {
        if (histogram == null)
        {
            System.out.println("Nieprawidłowa długośc histogramu");
            return;
        }
        int width = 256;
        int height = 256;
        BufferedImage histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = histogramImage.getGraphics();

        graphics.setColor(Color.WHITE); // Wypełnienie tła na biało od (0, 0) do (256 256)
        graphics.fillRect(0, 0, width, height);

        int maxCount = 0;
        for (int count : histogram)
        {
            maxCount = Math.max(maxCount, count);
        }

        graphics.setColor(Color.BLACK);
        for (int i = 0; i < histogram.length; i++)  // wypełnienie tła na czarno, ale tylko ta część zgadzająca się z tablicą:
        {
            int barHeight = (int) ((double) histogram[i] / maxCount * (height - 20)); // Wysokość
            int x = i;
            int y = height - 10 - barHeight;    // czubek
            graphics.drawLine(x, height - 10, x, y);
        }

        // zapis do pliku

        try
        {
            File outputFile = new File(path);
            ImageIO.write(histogramImage, "png", outputFile);
            System.out.println("Obraz histogramu został wygenerowany i zapisany pomyślnie.");
        }
        catch (Exception e) {
            System.out.println("Wystąpił błąd podczas zapisywania obrazu histogramu: " + e.getMessage());
        }

    }
}
