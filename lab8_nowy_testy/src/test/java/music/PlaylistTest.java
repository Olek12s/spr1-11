package music;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest
{
    @Test
    public void testNewPlaylistIsEmpty()
    {
        Playlist playlist = new Playlist();
        assertEquals(true, playlist.isEmpty());
    }

    @Test
    public void testAddSongIncreasesPlaylistSize()
    {
        Playlist playlist = new Playlist();
        Song song = new Song("Artysta", "Tytul", 300);
        playlist.add(song);
        assertEquals(1, playlist.size());
    }

    @Test
    public void testIfSongIsInPlaylist()
    {
        Playlist playlist = new Playlist();
        Song song = new Song("Artysta", "Tytul", 300);
        playlist.add(song);
        assertEquals(song, playlist.get(0));
    }

    @Test
    public void testIfThisSongIsInPlaylist()
    {
        Playlist playlist = new Playlist();
        Song song1 = new Song("Artysta", "Tytul", 300);
        playlist.add(song1);
        Song song2 = new Song("Artysta", "Tytul", 300);
        assertEquals(song1, song2);
    }

    @Test
    public void testAtSecond()
    {
        Playlist playlist = new Playlist();

        Song song1 = new Song("Artist1", "Title1", 200);
        Song song2 = new Song("Artist2", "Title2", 300);
        Song song3 = new Song("Artist3", "Title3", 400);

        playlist.add(song1);
        playlist.add(song2);
        playlist.add(song3);
        assertEquals(song1, playlist.atSecond(150));
        assertEquals(song2, playlist.atSecond(250));
        assertEquals(song3, playlist.atSecond(550));
        int whichSecond = 250;
        assertEquals(song1, playlist.atSecond(whichSecond));
    }

    @Test
    public void testAtSecondException()
    {
        Playlist playlist = new Playlist();

        Song song1 = new Song("Artist1", "Title1", 200);
        Song song2 = new Song("Artist2", "Title2", 300);
        Song song3 = new Song("Artist3", "Title3", 400);

        playlist.add(song1);
        playlist.add(song2);
        playlist.add(song3);

        assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(1000),
                "Expected IndexOutOfBoundsException for time exceeding the total duration of the playlist!");
    }

    @Test
    public void testAtSecondNegativeTime()
    {
        Playlist playlist = new Playlist();
        Song song1 = new Song("Artist1", "Title1", 200); // 200
        Song song2 = new Song("Artist2", "Title2", 300); // 300

        playlist.add(song1);
        playlist.add(song2);

        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(-1));
        assertEquals("Negative time: -1", exception.getMessage(), "Too short");
    }

    @Test
    public void testAtSecondTooBigTime()
    {
        Playlist playlist = new Playlist();
        Song song1 = new Song("Artist1", "Title1", 200); // 200
        Song song2 = new Song("Artist2", "Title2", 300); // 300

        playlist.add(song1);
        playlist.add(song2);

        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(1000));
        assertEquals("Time exceeds playlist duration: 1000", exception.getMessage(), "Too long");
    }
}