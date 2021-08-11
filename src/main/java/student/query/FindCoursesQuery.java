package student.query;

import command.RecordToTableElement;
import org.example.models.tables.pojos.Course;
import org.example.models.tables.records.StudentCourseRecord;
import org.jooq.DSLContext;
import query.GetTableQuery;
import query.Query;
import storage.PostgresConnectionFactory;
import student.NoGradeException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.example.models.Tables.COURSE;
import static org.example.models.tables.StudentCourse.STUDENT_COURSE;

public class FindCoursesQuery implements Query<List<Course>> {
    private DSLContext sql;
    private Long id;

    public FindCoursesQuery(DSLContext sql, Long id) {
        this.sql = sql;
        this.id = id;
    }

    @Override
    public List<Course> execute() {
        var query = sql.fetch(STUDENT_COURSE, STUDENT_COURSE.FK_STUDENT_ID.eq(id));

        if (query.isEmpty()) {
            throw new NoSuchElementException("student isn't enrolled in any course");
        }

        var courseList = new ArrayList<Course>();

        for (StudentCourseRecord currRecord : query) {
            int currId = currRecord.getFkCourseId();

            courseList.add(findCourseById(currId).get());
        }
        return courseList;
    }

    public Optional<Course> findCourseById(Integer id) {
        var courseRecord = sql.fetchOne(COURSE, COURSE.COURSE_ID.eq(id));

        if (courseRecord == null) {
            return Optional.empty();
        }

        var course = RecordToTableElement.recordToCourse(courseRecord);

        return Optional.of(course);
    }

    public int getGrade(int courseId) {
        var studentCourse = sql.fetchOne(STUDENT_COURSE, STUDENT_COURSE.FK_STUDENT_ID.eq(id), STUDENT_COURSE.FK_COURSE_ID.eq(courseId));

        if (studentCourse == null) {
            throw new NoSuchElementException("student isn't enrolled in given course");
        }

        if (studentCourse.getGrade() == null) {
            throw new NoGradeException("student has not been graded in given course");
        }

        return studentCourse.getGrade();
    }

    public List<Course> notEnrolledInCourses() {
        List<Course> notEnrolledCourses = GetTableQuery.courseTable(PostgresConnectionFactory.build());
        List<Course> enrolledCourses;

        try {
            enrolledCourses = this.execute();
        } catch (NoSuchElementException e) {
            return notEnrolledCourses;
        }

        var toRemove = new ArrayList<Course>();

        for (Course currCourse : notEnrolledCourses) {
            for (Course currEnrolledCourse : enrolledCourses) {
                if (currCourse.getCourseId() == currEnrolledCourse.getCourseId()) {
                    toRemove.add(currCourse);
                }
            }
        }

        for (Course toRemoveCourse : toRemove) {
            notEnrolledCourses.remove(toRemoveCourse);
        }

        return notEnrolledCourses;
    }
}
