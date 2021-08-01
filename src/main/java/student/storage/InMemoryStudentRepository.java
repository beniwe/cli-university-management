package student.storage;

import Exceptions.StudentAlreadyEnrolledException;
import org.example.models.tables.pojos.Student;
import org.jooq.Delete;

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
    public Student enroll(Student student) throws StudentAlreadyEnrolledException {
        for (Student currStudent : students) {
            if (currStudent.getId().equals(student.getId())) {
                throw new StudentAlreadyEnrolledException("ID already exists");
            }
        }
        this.students.add(student);

        return student;
    }

    @Override
    public Student remove(String id) {
        if (id.length() != 8) {
            throw new IllegalArgumentException("wrong input");
        }

        for (Student currentStudent : students) {
            if (currentStudent.getId().equals(id)) {
                students.remove(currentStudent);

                return currentStudent;
            }
        }

        return null;
    }
}
