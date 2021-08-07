package authentication;

import org.example.models.tables.pojos.Professor;
import org.example.models.tables.pojos.Student;

public class User {
    private Professor professor;
    private Student student;

    public User(Professor professor) {
        this.professor = professor;
    }

    public User(Student student) {
        this.student = student;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Student getStudent() {
        return student;
    }
}
