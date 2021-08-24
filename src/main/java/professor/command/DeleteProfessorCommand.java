package professor.command;

import command.Command;
import professor.storage.ProfessorRepository;

public class DeleteProfessorCommand implements Command {
    private ProfessorRepository repository;
    private Long professorId;

    public DeleteProfessorCommand(ProfessorRepository repository, Long professorId) {
        this.repository = repository;
        this.professorId = professorId;
    }

    @Override
    public void execute() {
        repository.removeProfessor(professorId);

        System.out.println("\nProfessor with ID (" + professorId + ") is now no longer in the system");
    }
}
