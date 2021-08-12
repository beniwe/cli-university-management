package student.query;

import org.example.models.tables.pojos.Student;
import org.example.models.tables.records.StudentCourseRecord;
import org.jooq.DSLContext;
import query.Query;
import storage.PostgresConnectionFactory;
import student.storage.PostgreSqlStudentRepository;
import student.storage.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.example.models.Tables.STUDENT_COURSE;

public class FindStudentQuery implements Query<Optional<Student>> {
  private final StudentRepository studentRepository;
  private final Long studentId;

  public FindStudentQuery(StudentRepository studentRepository, Long studentId) {
    this.studentRepository = studentRepository;
    this.studentId = studentId;
  }

  public List<Student> getStudentsInCourse(int courseId) {
    DSLContext sql = PostgresConnectionFactory.build();

    List<Student> result = new ArrayList<>();
    var records = sql.fetch(STUDENT_COURSE, STUDENT_COURSE.FK_COURSE_ID.eq(courseId));

    if (records.isEmpty()) {
      throw new NoSuchElementException("(!) No students enrolled in this course");
    }
    for (StudentCourseRecord currRecord : records) {
      var repository = new PostgreSqlStudentRepository(sql);
      var findStudent = new FindStudentQuery(repository, currRecord.getFkStudentId());

      Student currStudent = findStudent.execute().get();
      Long sessionStudentId = studentId;

      if (!(sessionStudentId.equals(currStudent.getStudentId()))) {
        result.add(currStudent);
      }
    }

    if (result.isEmpty()) {
      throw new IllegalStateException("(!) You are the only student in this course");
    }

    return result;
  }

  @Override
  public Optional<Student> execute() {
    return studentRepository.findStudentById(studentId);
  }

  public List<Student> getNonGradedStudentsInCourse(int courseId) {
    DSLContext sql = PostgresConnectionFactory.build();

    List<Student> result = new ArrayList<>();
    var records = sql.fetch(STUDENT_COURSE, STUDENT_COURSE.FK_COURSE_ID.eq(courseId));

    if (records.isEmpty()) {
      throw new NoSuchElementException("(!) No students enrolled in this course");
    }
    for (StudentCourseRecord currRecord : records) {
      var repository = new PostgreSqlStudentRepository(sql);
      var findStudent = new FindStudentQuery(repository, currRecord.getFkStudentId());

      Student currStudent = findStudent.execute().get();
      boolean isGraded = findStudent.gradedCheck(courseId);
      Long sessionStudentId = studentId;

      if (!isGraded && !(sessionStudentId.equals(currStudent.getStudentId()))) {

        result.add(currStudent);
      }
    }

    if (result.isEmpty()) {
      throw new IllegalStateException("(!) Either every student has already been graded or you are the only student enrolled in this course");
    }

    return result;
  }

  private boolean gradedCheck(int courseId) {
    var sql = PostgresConnectionFactory.build();
    var studentCourseRecord = sql.fetchOne(STUDENT_COURSE, STUDENT_COURSE.FK_STUDENT_ID.eq(studentId), STUDENT_COURSE.FK_COURSE_ID.eq(courseId));

    if (studentCourseRecord.getGrade() != null) {
      return true;
    }

    return false;
  }
}
