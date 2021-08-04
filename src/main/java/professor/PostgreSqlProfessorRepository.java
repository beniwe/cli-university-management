package professor;

import static org.example.models.Tables.PROFESSOR;

import java.util.Optional;
import org.example.models.tables.pojos.Professor;
import org.example.models.tables.records.ProfessorRecord;
import org.jooq.DSLContext;

public class PostgreSqlProfessorRepository implements ProfessorRepository {
  private final DSLContext sql;

  public PostgreSqlProfessorRepository(DSLContext sql) {
    this.sql = sql;
  }

  private static org.example.models.tables.pojos.Professor recordToProfessor(
      ProfessorRecord record) {
    return new org.example.models.tables.pojos.Professor(
        record.getProfessorId(),
        record.getName(),
        record.getBirthDate(),
        record.getPassword(),
        record.getIsAdmin());
  }

  @Override
  public Optional<org.example.models.tables.pojos.Professor> findProfessorById(Long id) {
    var professor = sql.fetchOne(PROFESSOR, PROFESSOR.PROFESSOR_ID.eq(id));
    if (professor == null) {
      return Optional.empty();
    }

    return Optional.of(recordToProfessor(professor));
  }

  @Override
  public Professor professorLoginCheck(Long id, String hashedPassword) {
    var maybeProfessor = this.findProfessorById(id);

    if (maybeProfessor.isEmpty()) {
      throw new IllegalArgumentException("wrong ID");
    }

    var professor = maybeProfessor.get();

    if (!professor.getPassword().equals(hashedPassword)) {
      throw new IllegalArgumentException("wrong Password");
    }

    return professor;
  }
}
