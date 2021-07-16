package student.storage;

import org.example.models.tables.pojos.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryStudentRepository implements StudentRepository {
    private final List<Student> students;

    public InMemoryStudentRepository() {
        this.students = new ArrayList<>();
    }

    @Override
    public Optional<Student> findById(String id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst();
    }

    @Override
    public Student enroll(Student student) {
        this.students.add(student);

        return student;
    }
}
