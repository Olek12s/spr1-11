package music;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song>
{

    public Song atSecond(int seconds)
    {
        int sumOfSeconds = 0;

        if (seconds < 0)
        {
            throw new IndexOutOfBoundsException("Too small.");
        }

        for (Song song : this)
        {
            if (seconds < sumOfSeconds + song.duration())
            {
                return song;
            }
            sumOfSeconds += song.duration();
        }

        throw new IndexOutOfBoundsException("Time exceeds playlist duration: " + seconds);
    }
}
