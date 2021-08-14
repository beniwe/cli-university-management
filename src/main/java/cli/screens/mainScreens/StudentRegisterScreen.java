package cli.screens.mainScreens;

import cli.CliApplication;
import cli.screens.Screen;
import org.example.models.tables.pojos.Student;
import query.GetTableQuery;
import storage.PostgresConnectionFactory;
import student.command.EnrollStudentCommand;
import student.storage.StudentRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class StudentRegisterScreen implements Screen {
  private final StudentRepository studentRepository;

  public StudentRegisterScreen(StudentRepository studentRepository) {
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
        System.out.println(
            "\n" + CliApplication.sectionString("(!) wrong format...try again\n[year]-[month]-[day]"));
      }
    }

    printDegreePrograms();

    System.out.print("Degree program: ");
    int degreeProgram = in.nextInt();

    LocalDate enrolledSince = LocalDate.now();

    Student studentToEnroll =
        new Student(null, name, birthDate, degreeProgram, enrolledSince, password, false);

    new EnrollStudentCommand(studentRepository, studentToEnroll).execute();
  }

  private void printDegreePrograms() {
    List<String> degreeProgramList;
    String degreePrograms = "";

    degreeProgramList = GetTableQuery.degreeProgramTable(PostgresConnectionFactory.build());

    for (String currString : degreeProgramList) {
      degreePrograms += currString + "\n";
    }

    StringBuilder sb = new StringBuilder(degreePrograms);

    sb.deleteCharAt(degreePrograms.length() - 1);

    System.out.println("Degree Programs:\n" + CliApplication.sectionString(sb.toString()));
  }
}
