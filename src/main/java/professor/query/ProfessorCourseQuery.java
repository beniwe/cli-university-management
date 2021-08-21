package professor.query;

import command.RecordToTableElement;
import org.example.models.tables.pojos.Course;
import org.example.models.tables.records.CourseRecord;
import org.jooq.DSLContext;
import query.Query;
import student.query.FindCoursesQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.example.models.Tables.COURSE;

public class ProfessorCourseQuery implements Query<List<Course>> {
    private DSLContext sql;
    private Long professorId;

    public ProfessorCourseQuery(DSLContext sql, Long professorId) {
        this.sql = sql;
        this.professorId = professorId;
    }

    @Override
    public List<Course> execute() {
        List<Course> result = new ArrayList<>();
        var records = sql.fetch(COURSE, COURSE.ASSIGNED_PROFESSOR.eq(professorId));

        if (records.isEmpty()) {
            throw new NoSuchElementException("Professor hasn't occupied any courses");
        }

        for (CourseRecord currRecord : records) {
            var coursesQuery = new FindCoursesQuery(sql, null);

            Course currCourse = coursesQuery.findCourseById(currRecord.getCourseId()).get();

            result.add(currCourse);
        }

        return result;
    }

    public List<Course> getNonOccupiedCourses() {
        List<Course> result = new ArrayList<>();
        var records = sql.fetch(COURSE, COURSE.ASSIGNED_PROFESSOR.isNull());

        if (records.isEmpty()) {
            throw new NoSuchElementException("(!) Either no Courses in the System you are assigned in every course");
        }

        for (CourseRecord currRecord : records) {
            Course currCourse = RecordToTableElement.recordToCourse(currRecord);

            result.add(currCourse);
        }

        return result;
    }
}
