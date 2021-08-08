package course.storage;

import static org.example.models.Tables.*;

import org.example.models.tables.pojos.Course;
import org.example.models.tables.records.CourseRecord;
import org.jooq.DSLContext;

import java.util.Optional;

public class CourseRepository {
    private DSLContext sql;

    public CourseRepository(DSLContext sql) {
        this.sql = sql;
    }

    public Course recordToCourse(CourseRecord record) {
        return new Course(
                record.getCourseId(),
                record.getName(),
                record.getAssignedProfessor());
    }

    public Optional<Course> findCourseById(int id) {
        var courseRecord = sql.fetchOne(COURSE, COURSE.COURSE_ID.eq(id));

        var courses = sql.fetch(STUDENT_COURSE,STUDENT_COURSE.FK_STUDENT_ID.eq(10000l));

        if (courseRecord == null) {
            return Optional.empty();
        }

        var course = recordToCourse(courseRecord);

        return Optional.of(course);
    }
}
