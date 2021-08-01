package student.storage;

import Exceptions.StudentAlreadyEnrolledException;
import java.util.Optional;
import org.example.models.tables.pojos.Student;

public interface StudentRepository {
  Optional<Student> findById(String id);

  Student enroll(Student student) throws StudentAlreadyEnrolledException;

  Student remove(String id);
}
