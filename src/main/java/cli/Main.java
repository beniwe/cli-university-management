 package cli;

import storage.PostgresConnectionFactory;
import student.storage.PostgreSqlStudentRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        final var sql = PostgresConnectionFactory.build();
        final var studentRepository = new PostgreSqlStudentRepository(sql);
        // CLI = Command Line Interface
        final var application = new CliApplication(studentRepository);

        application.run();
    }

}
