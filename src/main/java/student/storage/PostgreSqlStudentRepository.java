package student.storage;

import static org.example.models.Tables.STUDENT;

import java.util.Optional;
import org.example.models.tables.pojos.Student;
import org.example.models.tables.records.StudentRecord;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;

public class PostgreSqlStudentRepository implements StudentRepository {
  private final DSLContext sql;

  public PostgreSqlStudentRepository(DSLContext sql) {
    this.sql = sql;
  }

  private static Student recordToStudent(StudentRecord record) {
    return new Student(
        record.getId(),
        record.getName(),
        record.getBirthDate(),
        record.getEnrolledIn(),
        record.getEnrolledSince(),
        record.getPassword(),
        record.getCourseAssistant());
  }

  @Override
  public Optional<Student> findById(String id) {
    var student = sql.fetchOne(STUDENT, STUDENT.ID.eq(id));
    if (student == null) {
      return Optional.empty();
    }

    return Optional.of(recordToStudent(student));
  }

  @Override
  public Student enroll(Student student) throws DataAccessException {
    var record =
        sql.insertInto(
                STUDENT,
                STUDENT.ID,
                STUDENT.NAME,
                STUDENT.BIRTH_DATE,
                STUDENT.ENROLLED_IN,
                STUDENT.ENROLLED_SINCE,
                STUDENT.PASSWORD,
                STUDENT.COURSE_ASSISTANT)
            .values(
                student.getId(),
                student.getName(),
                student.getBirthDate(),
                student.getEnrolledIn(),
                student.getEnrolledSince(),
                student.getPassword(),
                student.getCourseAssistant())
            .returning(STUDENT.asterisk())
            .fetchOne();

    return recordToStudent(record);
  }

  @Override
  public Student remove(String id) {
    if (id.length() != 8) {
      throw new IllegalArgumentException("wrong input");
    }

    var toRemove =
        sql.deleteFrom(STUDENT).where(STUDENT.ID.eq(id)).returning(STUDENT.asterisk()).fetchOne();

    return this.recordToStudent(toRemove);
  }
}
