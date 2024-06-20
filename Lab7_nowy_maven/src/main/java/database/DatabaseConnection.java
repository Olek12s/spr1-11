package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static void connect(String path)
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            System.out.println("polaczono: " + path);
        } catch (SQLException e) {
            throw new RuntimeException("error", e);
        }
    }

    public static void disconnect()
    {
        try
        {
            if (connection != null && !connection.isClosed())
            {
                connection.close();
                System.out.println("rozlaczono");
            }
        } catch (SQLException e) {
            throw new RuntimeException("error", e);
        }
    }

    public static ResultSet selectQuery(String query)
    {
        ResultSet rs = null;
        try
        {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException("blad przy wykonywaniu kwerendy", e);
        }
        return rs;
    }

    public static void insertQuery(String query)
    {
        try
        {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("kwerenda wykonana");
        } catch (SQLException e) {
            throw new RuntimeException("blad przy wykonywaniu kwerendy", e);
        }
    }
    public static void insertTrack(int ID, String artist, String name, int duration)
    {
        try {
            Statement insertStatement = connection.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO song ");
            sb.append("VALUES ");
            sb.append(String.format("(%d, '%s', '%s', %d)",
                    ID, artist, name, duration));

            insertStatement.executeUpdate(sb.toString());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readTrack()
    {
        try {
            Statement selectionStatement = connection.createStatement();
            String query = "SELECT artist FROM song WHERE id = 20;";
            ResultSet rs = selectionStatement.executeQuery(query);
            while (rs.next())
            {
                System.out.println("artist of song nr 20 is " + rs.getString("artist"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
