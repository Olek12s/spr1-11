package auth;

import database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager
{
    DatabaseConnection connection;

    public AccountManager(DatabaseConnection connection)
    {
        this.connection = connection;
    }

    public Account getAccount(String name) throws SQLException
    {
        PreparedStatement statement = connection.getConnection()
                .prepareStatement("SELECT id, name FROM person WHERE name = ?;");
        statement.setString(1, name);
        statement.execute();

        ResultSet result = statement.getResultSet();
        if(result.next()) {
            int id = result.getInt("id");
            return new Account(id, name);
        }
        throw new RuntimeException("Brak uzytkownika:  " + name);
    }

    public int register(String name, String password) throws SQLException
    {
        PreparedStatement statement = connection.getConnection()
                .prepareStatement("SELECT * FROM auth_account WHERE name = ?;");
        statement.setString(1, name);

        statement.execute();
        if(statement.getResultSet().next())
            throw new RuntimeException("Użytkownik " + name + " istnieje.");

        statement = connection.getConnection().prepareStatement("INSERT INTO auth_account(name, password) VALUES (?, ?);");
        statement.setString(1, name);
        statement.setString(2, password);
        statement.executeUpdate();

        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public boolean login(String name, String password) throws SQLException
    {
        PreparedStatement statement = connection.getConnection()
                .prepareStatement("SELECT password FROM auth_account WHERE name = ?;");

        statement.setString(1, name);
        statement.execute();

        ResultSet result = statement.getResultSet();
        if (result.next())
        {
            String storedPassword = result.getString("password");
            return password.equals(storedPassword);
        }
        else
        {
            throw new RuntimeException("Brak użytkownika " + name);
        }
    }
}
