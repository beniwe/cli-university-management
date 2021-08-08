package professor;

import java.util.Optional;

public interface ProfessorRepository {

  Optional<org.example.models.tables.pojos.Professor> findProfessorById(Long id);
}
