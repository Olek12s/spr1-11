package music;

import java.sql.*;
import java.util.Optional;

public record Song(String artist, String title, int duration)
{
    public static class Persistence
    {
        private static final String DB = "jdbc:sqlite:songs.db";

        public static Optional<Song> read(int id)
        {
            Optional<Song> song = Optional.empty();

            try
            {
                Connection conn = DriverManager.getConnection(DB);
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM song WHERE id = " + id);

                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next())
                {
                    String artist = rs.getString("artist");
                    String title = rs.getString("title");
                    int duration = rs.getInt("length");
                    song = Optional.of(new Song(artist, title, duration));
                }
            } catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
            return song;
        }
    }
}
