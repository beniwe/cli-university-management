package student.query;

import org.example.models.tables.pojos.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import student.storage.InMemoryStudentRepository;

import java.time.LocalDate;

class FindStudentQueryTest {
    @Test
    void execute() {
        var repository = new InMemoryStudentRepository();
        var expectedId = "1";
        var expectedName = "Max Mustermann";
        repository.enroll(new Student(expectedId, expectedName, LocalDate.now(), 1, LocalDate.now()));

        var findStudentQuery = new FindStudentQuery(repository, "1");
        var student = findStudentQuery.execute();

        Assertions.assertTrue(student.isPresent());
        Assertions.assertEquals(expectedId, student.get().getId());
        Assertions.assertEquals(expectedName, student.get().getName());
    }
}