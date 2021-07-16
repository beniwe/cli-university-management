package cli.screens;

import org.example.models.tables.pojos.Student;
import student.command.EnrollStudentCommand;
import student.storage.StudentRepository;

import java.time.LocalDate;
import java.util.Scanner;

public class EnrollStudentScreen implements Screen {
    private final StudentRepository studentRepository;

    public EnrollStudentScreen(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void show(Scanner in) {
        System.out.print("ID: ");
        String id = in.nextLine();

        System.out.print("Name: ");
        String name = in.nextLine();

        System.out.print("Date of birth: ");
        LocalDate birthDate = LocalDate.parse(in.nextLine());

        System.out.print("Course ID: ");
        int courseId = in.nextInt();
        in.nextLine();

        LocalDate enrolledSince = LocalDate.now();

        Student studentToEnroll = new Student(id, name, birthDate, courseId, enrolledSince);

        new EnrollStudentCommand(studentRepository, studentToEnroll)
                .execute();
    }
}
