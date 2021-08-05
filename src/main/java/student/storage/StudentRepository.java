package student.storage;

import student.StudentAlreadyEnrolledException;
import java.util.Optional;
import org.example.models.tables.pojos.Student;

public interface StudentRepository {
  Optional<Student> findStudentById(Long id);

  Student enroll(Student student) throws StudentAlreadyEnrolledException;

  Student remove(Long id);
}
