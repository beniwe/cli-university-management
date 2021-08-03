package professor;

import org.example.models.tables.pojos.Professor;

import java.util.Optional;

public interface ProfessorRepository {

    Optional<org.example.models.tables.pojos.Professor> findProfessorById(Long id);

    Professor professorLoginCheck(Long id, String hashedPassword);
}
