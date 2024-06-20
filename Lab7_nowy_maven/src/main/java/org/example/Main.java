package org.example;


import auth.AccountManager;
import database.DatabaseConnection;
import database.Song;

import java.sql.*;

public class Main
{
    public static void main(String[] args)
    {
        DatabaseConnection.connect("songs.db");

        try // Odczyt całej tabeli songs
        {
            ResultSet rs = DatabaseConnection.selectQuery("SELECT * FROM songs");
            while (rs.next()) {
                System.out.print("name = " + rs.getString("name"));
                System.out.println(" id = " + rs.getInt("id"));
            }
        } catch (RuntimeException | SQLException e) {
            e.printStackTrace(System.err);
        }

        try // Wykonanie Query na tabeli songs
        {
            String query = "INSERT INTO songs VALUES (76, 'Krawczyk', 'Paro', 33)";
            DatabaseConnection.insertQuery(query);
        } catch (RuntimeException e) {
            e.printStackTrace(System.err);
        }

        DatabaseConnection.disconnect();



        DatabaseConnection.connect("shop.db");
        AccountManager accountManager = new AccountManager(new DatabaseConnection());
        try {
            accountManager.register("Adam123", "qwerty");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DatabaseConnection.disconnect();
    }
}
