package course.query;

import course.storage.CourseRepository;
import org.example.models.tables.pojos.Course;
import org.jooq.DSLContext;
import query.Query;

public class GetCourseQuery implements Query<Course> {
    private CourseRepository repository;

    public GetCourseQuery(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Course execute() {
        return null;
    }
}
