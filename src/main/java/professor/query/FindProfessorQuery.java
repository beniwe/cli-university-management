package professor.query;

import org.example.models.tables.pojos.Professor;
import professor.storage.ProfessorRepository;
import query.Query;

import java.util.Optional;

public class FindProfessorQuery implements Query<Optional<Professor>> {
    private ProfessorRepository repository;
    private Long professorId;

    public FindProfessorQuery(ProfessorRepository repository, Long professorId) {
        this.repository = repository;
        this.professorId = professorId;
    }

    @Override
    public Optional<Professor> execute() {
        return repository.findProfessorById(professorId);
    }
}
