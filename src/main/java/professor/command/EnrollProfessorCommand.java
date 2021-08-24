package professor.command;

import command.Command;
import org.example.models.tables.pojos.Professor;
import professor.storage.PostgreSqlProfessorRepository;

public class EnrollProfessorCommand implements Command {
    private PostgreSqlProfessorRepository repository;
    private Professor professor;

    public EnrollProfessorCommand(PostgreSqlProfessorRepository repository, Professor professor) {
        this.repository = repository;
        this.professor = professor;
    }

    @Override
    public void execute() {
        Long id = repository.enrollProfessor(professor);

        System.out.println("\nSuccessfully registered!\nID (" + id + ")");
    }
}
