package cli.screens;

import cli.CliApplication;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import org.example.models.tables.pojos.Student;
import query.GetTableQuery;
import storage.PostgresConnectionFactory;
import student.command.EnrollStudentCommand;
import student.storage.StudentRepository;

public class RegisterScreen implements Screen {
  private final StudentRepository studentRepository;

  public RegisterScreen(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public void show(Scanner in) {

    System.out.print("Name: ");
    String name = in.nextLine();

    System.out.print("Password: ");
    String password = in.nextLine();

    LocalDate birthDate;

    while (true) {
      System.out.print("Date of birth: ");
      try {
        birthDate = LocalDate.parse(in.nextLine());

        break;

      } catch (DateTimeParseException e) {
        System.out.println("\n" + CliApplication.sectionString("wrong format...try again\n[year]-[month]-[day]"));
      }
    }

    String listOfDegreePrograms = "Degree Programs:\n";

    try {
      listOfDegreePrograms += GetTableQuery.degreeProgrammTable(PostgresConnectionFactory.build());
    } catch (SQLException throwables) {
      throw new IllegalStateException(throwables.getCause());
    }

    System.out.println(CliApplication.sectionString(listOfDegreePrograms));

    System.out.print("Degree program: ");
    int degreeProgram = in.nextInt();

    LocalDate enrolledSince = LocalDate.now();

    Student studentToEnroll =
        new Student(null, name, birthDate, degreeProgram, enrolledSince, password, false);

    new EnrollStudentCommand(studentRepository, studentToEnroll).execute();
  }
}
