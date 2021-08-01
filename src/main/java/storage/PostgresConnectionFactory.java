package storage;

import java.sql.DriverManager;
import java.sql.SQLException;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class PostgresConnectionFactory {
  private static final String CONNECTION_STRING =
      "jdbc:postgresql://localhost:5432/university_management";
  private static final String DATABASE_USER = "postgres";
  private static final String DATABASE_PASSWORD = "postgres";

  // build tries to establish a connection to a locally running PostgreSQL server.
  public static DSLContext build() throws SQLException {
    final var connection =
        DriverManager
            // TODO: try to load values from environment first and use defaults if they should not
            // exist
            .getConnection(CONNECTION_STRING, DATABASE_USER, DATABASE_PASSWORD);

    return DSL.using(connection, SQLDialect.POSTGRES);
  }
}
