package professor;

import java.util.Optional;
import org.example.models.tables.pojos.Professor;

public interface ProfessorRepository {

  Optional<org.example.models.tables.pojos.Professor> findProfessorById(Long id);

  Professor professorLoginCheck(Long id, String hashedPassword);
}
