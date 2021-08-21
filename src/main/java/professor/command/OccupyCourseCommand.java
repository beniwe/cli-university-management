package professor.command;

import command.Command;
import org.jooq.DSLContext;

import static org.example.models.tables.Course.COURSE;

public class OccupyCourseCommand implements Command {
    private DSLContext sql;
    private Long professorId;
    private Integer courseId;

    public OccupyCourseCommand(DSLContext sql, Long professorId, Integer courseId) {
        this.sql = sql;
        this.professorId = professorId;
        this.courseId = courseId;
    }

    @Override
    public void execute() {
        var courseRecord = sql.fetchOne(COURSE, COURSE.COURSE_ID.eq(courseId));

        if (courseRecord.getAssignedProfessor() != null) {
            throw new IllegalStateException("Course is already occupied");
        }

        sql.update(COURSE).
                set(COURSE.ASSIGNED_PROFESSOR, professorId).
                where(COURSE.COURSE_ID.eq(courseId)).
                execute();
    }
}
