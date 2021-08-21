package professor.command;

import command.Command;
import professor.storage.ProfessorRepository;

public class AssignAdminCommand implements Command {
    private ProfessorRepository repository;
    private Long professorId;

    public AssignAdminCommand(ProfessorRepository repository, Long professorId) {
        this.repository = repository;
        this.professorId = professorId;
    }

    @Override
    public void execute() {
        repository.assignAdmin(professorId);

        System.out.println("\nProfessor with ID (" + professorId + ") is now an Admin");
    }
}
