package cli.screens;

import org.example.models.tables.pojos.Student;
import student.command.EnrollStudentCommand;
import student.storage.StudentRepository;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class LoginScreen implements Screen{
    private StudentRepository studentRepository;

    public LoginScreen(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void show(Scanner in) {
        System.out.print("ID: ");
        Long id = in.nextLong();

        System.out.print("Password: ");
        String password = in.nextLine();

        String hashedPassword;

        try {
            hashedPassword = EnrollStudentCommand.SHAHash(password);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getCause());
        }

        Student verification = studentRepository.studentLoginCheck(id, hashedPassword);
    }
}
