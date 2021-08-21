package cli.screens.adminScreen;

import cli.CliApplication;
import cli.screens.Screen;
import org.example.models.tables.pojos.Professor;
import professor.command.AssignAdminCommand;
import professor.query.FindProfessorQuery;
import professor.storage.PostgreSqlProfessorRepository;
import storage.PostgresConnectionFactory;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AssignAdminScreen implements Screen {
    private Professor professor;

    public AssignAdminScreen(Professor professor) {
        this.professor = professor;
    }

    @Override
    public void show(Scanner in) {
        int professorChoice;
        Long professorId;

        while (true) {

            List<Long> professorIds = null;

            try {
                professorIds = printNonAdminProfessors();
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());

                return;
            }   catch (RuntimeException e) {
                System.out.println(e.getMessage());

                return;
            }

            System.out.print("Professor: ");

            try {
                professorChoice = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("(!) Invalid input");

                in.nextLine();
                continue;
            }

            if (professorChoice >= 1 && professorChoice <= professorIds.size()) {
                professorId = professorIds.get(professorChoice - 1);

                break;
            } else {
                System.out.println("(!) Invalid input");

                continue;
            }
        }

        var repository = new PostgreSqlProfessorRepository(PostgresConnectionFactory.build());
        var assignAdmin = new AssignAdminCommand(repository, professorId);

        assignAdmin.execute();

        in.nextLine();
    }

    public List<Long> printNonAdminProfessors() {
        String professors = "";

        List<Long> professorIds = new ArrayList<>();
        var repository = new PostgreSqlProfessorRepository(PostgresConnectionFactory.build());
        FindProfessorQuery professorQuery = new FindProfessorQuery(repository, professor.getProfessorId());

        List<Professor> professorList = null;

        professorList = professorQuery.getNonAdminProfessors();

        int listNumbers = 1;

        for (Professor currProfessor : professorList) {

            professors += String.format("(%d) %s | ID: %s\n", listNumbers, currProfessor.getName(), currProfessor.getProfessorId());

            professorIds.add(currProfessor.getProfessorId());

            listNumbers++;
        }

        StringBuilder sb = new StringBuilder(professors);

        sb.deleteCharAt(professors.length() - 1);

        System.out.println("\nChoose Professor:\n" + CliApplication.sectionString(sb.toString()));

        return professorIds;
    }
}
