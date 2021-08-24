package professor.storage;

import org.example.models.tables.pojos.Professor;

import java.util.Optional;

public interface ProfessorRepository {

  Optional<Professor> findProfessorById(Long id);

  void removeProfessor(Long professorId);

  void assignAdmin(Long professorId);
}
