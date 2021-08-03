package cli.screens;

import java.time.LocalDate;
import java.util.Scanner;
import org.example.models.tables.pojos.Student;
import student.command.EnrollStudentCommand;
import student.storage.StudentRepository;

public class RegisterScreen implements Screen {
  private final StudentRepository studentRepository;

  public RegisterScreen(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public void show(Scanner in) {
    System.out.print("ID: ");
    String id2 = in.nextLine();

    System.out.print("Name: ");
    String name = in.nextLine();

    System.out.print("Password: ");
    String password = in.nextLine();

    System.out.print("Date of birth: ");
    LocalDate birthDate = LocalDate.parse(in.nextLine());

    System.out.print("Course ID: ");
    int courseId = in.nextInt();
    in.nextLine();

    System.out.print("Course assistant (Y/N): ");
    String answer = in.nextLine();
    boolean courseAssistant;

    if (answer.equals("Y")) courseAssistant = true;
    else if (answer.equals("N")) courseAssistant = false;
    else throw new IllegalArgumentException("wrong input");

    LocalDate enrolledSince = LocalDate.now();

    Student studentToEnroll =
        new Student(null , name, birthDate, courseId, enrolledSince, password, courseAssistant);


    new EnrollStudentCommand(studentRepository, studentToEnroll).execute();

    System.out.println(studentRepository.getMaxStudentId());
  }
}