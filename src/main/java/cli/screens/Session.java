package cli.screens;

import org.example.models.tables.pojos.Professor;
import org.example.models.tables.pojos.Student;
import student.storage.StudentRepository;

import java.util.Scanner;

public class Session implements Screen{
    private StudentRepository studentRepository;
    private Professor professor;
    private Student student;


    public Session(StudentRepository studentRepository, Professor professor) {
        this.studentRepository = studentRepository;
        this.professor = professor;
    }

    public Session(StudentRepository studentRepository, Student student) {
        this.studentRepository = studentRepository;
        this.student = student;
    }

    @Override
    public void show(Scanner in) {
        if (student == null) {
            this.professorSession(in,professor);
        }

        else {
            this.studentSession(in,student);
        }
    }

    public void professorSession(Scanner in, Professor professor) {

    }

    public void studentSession(Scanner in, Student student) {

    }
}
