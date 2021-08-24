package authentication;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import storage.PostgresConnectionFactory;

class AuthenticateTest {

  @Test
  void authenticationTest() throws SQLException {
    DSLContext sql = PostgresConnectionFactory.build();

    Long existingId = 10000000l;
    String existingPassword = "123";

    Long nonExistingId = 1l;
    String nonExistingPassword = "nonExisting";

    Authenticate successfulLoginCheck = new Authenticate(sql, existingId, existingPassword);
    Authenticate failingLoginCheck = new Authenticate(sql, nonExistingId, nonExistingPassword);

    try {
      successfulLoginCheck.execute();

    } catch (IllegalArgumentException e) {
      fail("IllegalArgumentException was not expected");
    }

    try {
      failingLoginCheck.execute();

      fail("IllegalArgumentException was expected");

    } catch (IllegalArgumentException e) {
    }
    ;
  }
}
