package student.command;

import org.example.models.tables.pojos.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import student.storage.InMemoryStudentRepository;
import student.storage.StudentRepository;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;

class EnrollStudentCommandTest {

    @Test
    void enrollStudent() throws SQLException, NoSuchAlgorithmException {
        StudentRepository repo = new InMemoryStudentRepository();

        var student =
                new Student(null, "4324", LocalDate.now(), 1, LocalDate.now(), "123", true);

        var enroll = new EnrollStudentCommand(repo, student);

        enroll.execute();

        String expectedPassword = EnrollStudentCommand.SHAHash(student.getPassword());

        var maybeStudent = repo.findStudentById(student.getStudentId());

        Assertions.assertTrue(maybeStudent.isPresent());

        var studentInRepo = maybeStudent.get();

        Assertions.assertEquals(expectedPassword,studentInRepo.getPassword());
    }

}