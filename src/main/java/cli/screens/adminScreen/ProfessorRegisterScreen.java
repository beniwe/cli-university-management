package cli.screens.adminScreen;

import cli.CliApplication;
import cli.screens.Screen;
import org.example.models.tables.pojos.Professor;
import professor.command.EnrollProfessorCommand;
import professor.storage.PostgreSqlProfessorRepository;
import storage.PostgresConnectionFactory;
import student.command.EnrollStudentCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ProfessorRegisterScreen implements Screen {


    @Override
    public void show(Scanner in) {

        System.out.print("Name: ");
        String name = in.nextLine();

        System.out.print("Password: ");
        String hashedPassword = EnrollStudentCommand.SHAHash(in.nextLine());

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

        boolean isAdmin;

        while (true) {
            System.out.print("Admin (Y/N): ");
            String choice = in.nextLine();

            if (choice.equals("Y")) {
                isAdmin = true;

                break;
            }

            else if (choice.equals("N")) {
                isAdmin = false;

                break;
            }

            else {
                System.out.println("(!) Invalid input1");
            }
        }

        Professor professorToEnroll =
                new Professor(null, name, birthDate, hashedPassword, isAdmin);

        var repository = new PostgreSqlProfessorRepository(PostgresConnectionFactory.build());

        EnrollProfessorCommand enrollProfessor = new EnrollProfessorCommand(repository, professorToEnroll);

        enrollProfessor.execute();
    }
}
