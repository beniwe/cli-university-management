package student.query;

import java.sql.SQLException;
import java.time.LocalDate;
import org.example.models.tables.pojos.Student;
import org.junit.jupiter.api.Test;
import storage.PostgresConnectionFactory;
import student.storage.PostgreSqlStudentRepository;

public class RoleStudentTest {

  @Test
  void duplicateKeyTest() throws SQLException {
    var postgreRepository = new PostgreSqlStudentRepository(PostgresConnectionFactory.build());
    var student1 =
        new Student("12345678", "4324", LocalDate.now(), 1, LocalDate.now(), "123", true);
    var student2 =
        new Student("12345678", "4324", LocalDate.now(), 1, LocalDate.now(), "123", true);

    postgreRepository.enroll(student1);
    postgreRepository.enroll(student2);
  }
}
