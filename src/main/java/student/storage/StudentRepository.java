package student.storage;


import Exceptions.StudentAlreadyEnrolledException;
import org.example.models.tables.pojos.Student;

import java.util.Optional;

public interface StudentRepository {
    Optional<Student> findById(String id);
    Student enroll(Student student) throws StudentAlreadyEnrolledException;
    Student remove(String id);
}
