package student.storage;


import org.example.models.tables.pojos.Student;

import java.util.Optional;

public interface StudentRepository {
    Optional<Student> findById(String id);
    Student enroll(Student student);
}
