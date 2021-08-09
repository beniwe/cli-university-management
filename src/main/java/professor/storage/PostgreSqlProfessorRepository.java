package professor.storage;

import static org.example.models.Tables.PROFESSOR;

import java.util.Optional;

import org.example.models.tables.pojos.Professor;
import command.RecordToTableElement;
import org.jooq.DSLContext;

public class PostgreSqlProfessorRepository implements ProfessorRepository {
  private final DSLContext sql;

  public PostgreSqlProfessorRepository(DSLContext sql) {
    this.sql = sql;
  }



  @Override
  public Optional<Professor> findProfessorById(Long id) {
    var professor = sql.fetchOne(PROFESSOR, PROFESSOR.PROFESSOR_ID.eq(id));
    if (professor == null) {
      return Optional.empty();
    }

    return Optional.of(RecordToTableElement.recordToProfessor(professor));
  }
}
