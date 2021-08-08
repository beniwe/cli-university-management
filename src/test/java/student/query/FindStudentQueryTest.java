package student.query;

import java.time.LocalDate;
import org.example.models.tables.pojos.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import student.StudentAlreadyEnrolledException;
import student.storage.InMemoryStudentRepository;

class FindStudentQueryTest {
  @Test
  void execute() {
    var repository = new InMemoryStudentRepository();
    var expectedId = 12345678l;
    var expectedName = "Max Mustermann";
    var expectedPassword = "123";

    try {
      repository.enroll(
          new Student(
              expectedId,
              expectedName,
              LocalDate.now(),
              1,
              LocalDate.now(),
              expectedPassword,
              false));
    } catch (StudentAlreadyEnrolledException e) {
      e.printStackTrace();
    }

    var findStudentQuery = new FindStudentQuery(repository, 10000000l);
    var student = findStudentQuery.execute();

    Assertions.assertTrue(student.isPresent());
    Assertions.assertEquals(expectedId, student.get().getStudentId());
    Assertions.assertEquals(expectedName, student.get().getName());
  }
}
