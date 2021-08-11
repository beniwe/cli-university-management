package student.storage;

import command.RecordToTableElement;
import org.example.models.tables.pojos.Course;
import org.example.models.tables.pojos.Student;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.example.models.Tables.STUDENT;
import static org.example.models.Tables.STUDENT_COURSE;
import static org.jooq.impl.DSL.max;

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

    sql.deleteFrom(STUDENT_COURSE).where(STUDENT_COURSE.FK_STUDENT_ID.eq(id)).execute();

    var toRemove =
        sql.deleteFrom(STUDENT)
            .where(STUDENT.STUDENT_ID.eq(id))
            .returning(STUDENT.asterisk())
            .fetchOne();

    return RecordToTableElement.recordToStudent(toRemove);
  }

  public void addStudentToCourse(Student student, Course course) {
    sql.insertInto(
            STUDENT_COURSE,
            STUDENT_COURSE.FK_STUDENT_ID,
            STUDENT_COURSE.FK_COURSE_ID,
            STUDENT_COURSE.IS_COURSE_ASSISTANT)
    .values(
            student.getStudentId(),
            course.getCourseId(),
            false
    ).execute();

    System.out.println("You are now enrolled in: " + course.getName());
  }
}
