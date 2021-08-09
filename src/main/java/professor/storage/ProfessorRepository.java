package professor.storage;

import java.util.Optional;
import org.example.models.tables.pojos.Professor;

public interface ProfessorRepository {

  Optional<Professor> findProfessorById(Long id);
}
