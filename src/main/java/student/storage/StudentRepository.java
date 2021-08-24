package student.storage;

import java.util.Optional;
import org.example.models.tables.pojos.Student;
import student.StudentAlreadyEnrolledException;

public interface StudentRepository {
  Optional<Student> findStudentById(Long id);

  Student enroll(Student student) throws StudentAlreadyEnrolledException;

  Student remove(Long id);
}
