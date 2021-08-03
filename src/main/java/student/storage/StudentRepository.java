package student.storage;

import Exceptions.StudentAlreadyEnrolledException;
import java.util.Optional;
import org.example.models.tables.pojos.Student;

public interface StudentRepository {
  Optional<Student> findStudentById(Long id);

  Student studentLoginCheck(Long id, String hashedPassword);

  Student enroll(Student student) throws StudentAlreadyEnrolledException;

  Student remove(Long id);

  Long getMaxStudentId();
}
