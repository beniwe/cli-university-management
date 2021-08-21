package cli.screens.adminScreen;

import cli.CliApplication;
import cli.screens.Screen;
import org.example.models.tables.pojos.Professor;
import professor.command.DeleteProfessorCommand;
import professor.query.FindProfessorQuery;
import professor.storage.PostgreSqlProfessorRepository;
import storage.PostgresConnectionFactory;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DeleteProfessorScreen implements Screen {
    private Professor professor;

    public DeleteProfessorScreen(Professor professor) {
        this.professor = professor;
    }

    @Override
    public void show(Scanner in) {
        int professorChoice;
        var printMethod = new DeleteProfessorScreen(professor);

        List<Long> professorIds;
        Long professorId;

        while (true) {
            try {
                professorIds = printProfessors();
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
        var deleteProfessor = new DeleteProfessorCommand(repository, professorId);

        deleteProfessor.execute();

        in.nextLine();
    }

    public List<Long> printProfessors() {
        String professors = "";

        List<Long> professorIds = new ArrayList<>();
        var repository = new PostgreSqlProfessorRepository(PostgresConnectionFactory.build());
        FindProfessorQuery professorQuery = new FindProfessorQuery(repository, professor.getProfessorId());

        List<Professor> professorList = null;

        try {
            professorList = professorQuery.getProfessors();
        }   catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

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
