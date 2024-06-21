package org.example;

import java.awt.image.BufferedImage;

public class HistogramCalculationWorker implements Runnable
{
    private final int[] histogram;
    private final int channel;
    private final int startY;
    private final int endY;
    private final BufferedImage image;

    public HistogramCalculationWorker(int[] histogram, int channel, int startY, int endY, BufferedImage image)
    {
        this.histogram = histogram;
        this.channel = channel;
        this.startY = startY;
        this.endY = endY;
        this.image = image;
    }


    @Override
    public void run()
    {
        for (int y = startY; y < endY; y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
                int rgb = image.getRGB(x, y);
                int color = (rgb >> (channel * 8)) & 0xFF;
                synchronized (histogram)
                {
                    histogram[color]++;
                }
            }
        }
    }

}
