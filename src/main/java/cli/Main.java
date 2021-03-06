package cli;

import java.sql.SQLException;
import storage.PostgresConnectionFactory;

public class Main {
  public static void main(String[] args) throws SQLException {
    final var sql = PostgresConnectionFactory.build();
    final var application = new CliApplication(sql);

    application.run();
  }
}
