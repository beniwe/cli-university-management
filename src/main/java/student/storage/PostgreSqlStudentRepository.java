package student.storage;

import static org.example.models.Tables.*;
import static org.jooq.impl.DSL.max;

import java.util.NoSuchElementException;
import java.util.Optional;

import command.RecordToTableElement;
import org.example.models.tables.pojos.Student;
import org.example.models.tables.records.StudentRecord;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;

public class PostgreSqlStudentRepository implements StudentRepository {
  private final DSLContext sql;

  public PostgreSqlStudentRepository(DSLContext sql) {
    this.sql = sql;
  }

  @Override
  public Optional<Student> findStudentById(Long id) {
    var student = sql.fetchOne(STUDENT, STUDENT.STUDENT_ID.eq(id));
    if (student == null) {
      return Optional.empty();
    }

    return Optional.of(RecordToTableElement.recordToStudent(student));
  }

  @Override
  public Student enroll(Student student) throws DataAccessException {


    var record =
        sql.insertInto(
                STUDENT,
                STUDENT.NAME,
                STUDENT.BIRTH_DATE,
                STUDENT.ENROLLED_IN,
                STUDENT.ENROLLED_SINCE,
                STUDENT.PASSWORD,
                STUDENT.IS_COURSE_ASSISTANT)
            .values(
                student.getName(),
                student.getBirthDate(),
                student.getEnrolledIn(),
                student.getEnrolledSince(),
                student.getPassword(),
                student.getIsCourseAssistant())
            .returning(STUDENT.asterisk())
            .fetchOne();

    System.out.println("Your ID is: " + getMaxStudentId());

    return RecordToTableElement.recordToStudent(record);
  }

  private Long getMaxStudentId() {
    Long maxId = sql.select(max(STUDENT.STUDENT_ID)).from(STUDENT).fetchOne().component1();

    if (maxId == null) {
      throw new NoSuchElementException("no enrolled student");
    }

    return maxId;
  }

  @Override
  public Student remove(Long id) {

    var toRemove =
        sql.deleteFrom(STUDENT)
            .where(STUDENT.STUDENT_ID.eq(id))
            .returning(STUDENT.asterisk())
            .fetchOne();

    return RecordToTableElement.recordToStudent(toRemove);
  }
}
