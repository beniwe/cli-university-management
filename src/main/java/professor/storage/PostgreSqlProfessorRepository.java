package professor.storage;

import command.RecordToTableElement;
import org.example.models.tables.pojos.Professor;
import org.jooq.DSLContext;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.example.models.Tables.COURSE;
import static org.example.models.Tables.PROFESSOR;

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

  public Long enrollProfessor(Professor professor) {
    var record = sql.
            insertInto(PROFESSOR, PROFESSOR.NAME, PROFESSOR.BIRTH_DATE, PROFESSOR.PASSWORD, PROFESSOR.IS_ADMIN).
            values(professor.getName(), professor.getBirthDate(), professor.getPassword(), professor.getIsAdmin()).
            returning(PROFESSOR.asterisk()).
            fetchOne();

    return record.getProfessorId();
  }

  public void removeProfessor(Long professorId) {
    var existCheck = sql.fetchOne(PROFESSOR, PROFESSOR.PROFESSOR_ID.eq(professorId));

    if (existCheck == null) {
      throw new NoSuchElementException("Professor ID is not currently in the system");
    }

    sql.update(COURSE).
            setNull(COURSE.ASSIGNED_PROFESSOR).
            where(COURSE.ASSIGNED_PROFESSOR.eq(professorId)).
            execute();

    sql.deleteFrom(PROFESSOR).
            where(PROFESSOR.PROFESSOR_ID.eq(professorId)).
            execute();
  }

  public void assignAdmin(Long professorId) {
    var existCheck = sql.fetchOne(PROFESSOR, PROFESSOR.PROFESSOR_ID.eq(professorId));

    if (existCheck == null) {
      throw new NoSuchElementException("Professor ID is not currently in the system");
    }

    sql.update(PROFESSOR).
            set(PROFESSOR.IS_ADMIN, true).
            where(PROFESSOR.PROFESSOR_ID.eq(professorId)).
            execute();
  }
}
