package student.command;

import Exceptions.StudentAlreadyEnrolledException;
import command.Command;
import org.example.models.tables.pojos.Student;
import student.storage.StudentRepository;

public class EnrollStudentCommand implements Command {
    private final StudentRepository studentRepository;
    private final Student student;

    public EnrollStudentCommand(StudentRepository studentRepository, Student student) {
        this.studentRepository = studentRepository;
        this.student = student;
    }

    @Override
    public void execute() {
        if (student.getId().length() != 8) {
            // TODO: use a custom exception instead.
            throw new IllegalArgumentException("Student ID must be 8 characters long.");
        }

        // TODO: catch and identify duplicate key exception and throw StudentAlreadyEnrolledException instead.
        try {
            this.studentRepository.enroll(student);
        } catch (StudentAlreadyEnrolledException e) {
            e.printStackTrace();
        }
    }
}
