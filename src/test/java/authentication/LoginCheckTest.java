package authentication;

import authentication.LoginCheck;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import storage.PostgresConnectionFactory;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoginCheckTest {

    @Test
    void authenticationTest() throws SQLException {
        DSLContext sql = PostgresConnectionFactory.build();

        Long existingId = 10000000l;
        String existingPassword = "123";

        Long nonExistingId = 1l;
        String nonExistingPassword = "nonExisting";



        LoginCheck successfulLoginCheck = new LoginCheck(sql, existingId, existingPassword);
        LoginCheck failingLoginCheck = new LoginCheck(sql, nonExistingId, nonExistingPassword);

        try {
            successfulLoginCheck.execute();

        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException was not expected");
        }

        try {
            failingLoginCheck.execute();

            fail("IllegalArgumentException was expected");

        } catch (IllegalArgumentException e) {};
    }
}