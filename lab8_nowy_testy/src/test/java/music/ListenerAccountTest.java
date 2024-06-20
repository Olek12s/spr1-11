package music;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class ListenerAccountTest
{
    @Test
    public void testRegisterNewAccount()
    {
        String username = "login";
        String password = "password";

        int accountId;
        try {
            accountId = ListenerAccount.Persistence.register(username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        assertTrue(accountId > 0);

        //czy konto istnieje w bazie
        ListenerAccount account = null;
        try {
            account = ListenerAccount.Persistence.authenticate(username, password);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(account, "Account should be authenticated successfully");
    }

    @Test
    public void LoginTest()
    {
        String username = "login";
        String password = "password";
        int accountId;

        try
        {
            ListenerAccount account = ListenerAccount.Persistence.authenticate(username, password);
            assertNotNull(account, "Account should be authenticated successfully");
            assertEquals(username, account.getUsername(), "Username should match authenticated account");
        } catch (AuthenticationException e)
        {
            System.out.println(e.getMessage());
        }
    }
}