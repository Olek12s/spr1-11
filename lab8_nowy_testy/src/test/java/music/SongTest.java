package music;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class SongTest
{
    @Test
    public void openDatabaseTest()
    {
        DatabaseConnection.connect("songs.db");
    }

    @BeforeAll
    static void openDatabase()
    {
        DatabaseConnection.connect("songs.db");
    }

    @Test
    public void readSongTest1()
    {
        Optional<Song> testSong = Song.Persistence.read(5);
        Song song = new Song("Artist1", "Title1", 200);
        assertTrue(testSong.isPresent());
        assertEquals(song, testSong.get());
    }

    @Test
    public void readSongTest2()
    {
        Optional<Song> testSong = Song.Persistence.read(1);
        assertFalse(testSong.isPresent());  //sukces, gdy puste
    }

    @Test
    public void testReadNonExistentSong()
    {
        Optional<Song> testSong = Song.Persistence.read(1);
        assertFalse(testSong.isPresent(), "Expected no song for index 1");
    }

    @Test
    public void testReadExistingSong() {
        Optional<Song> testSong = Song.Persistence.read(2);
        assertTrue(testSong.isPresent(), "Expected song to be present for index 2");

        Song expectedSong = new Song("Artist", "Title", 300);
        Song actualSong = testSong.get();

        assertEquals(expectedSong.artist(), actualSong.artist(), "Artist mismatch for index 2");
        assertEquals(expectedSong.title(), actualSong.title(), "Title mismatch for index 2");
        assertEquals(expectedSong.duration(), actualSong.duration(), "Duration mismatch for index 2");
    }

    @AfterAll
    static void closeDatabase()
    {
        DatabaseConnection.disconnect();
    }
}