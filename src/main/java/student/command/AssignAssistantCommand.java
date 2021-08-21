package student.command;

import command.Command;
import org.example.models.tables.pojos.Student;
import org.jooq.DSLContext;
import storage.PostgresConnectionFactory;
import student.query.FindStudentQuery;
import student.storage.PostgreSqlStudentRepository;
import student.storage.StudentRepository;

import static org.example.models.tables.Student.STUDENT;
import static org.example.models.tables.StudentCourse.STUDENT_COURSE;

public class AssignAssistantCommand implements Command {
    private DSLContext sql;
    private Long studentId;
    private Integer courseId;

    public AssignAssistantCommand(DSLContext sql, Long studentId, Integer courseId) {
        this.sql = sql;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    @Override
    public void execute() {
        sql.update(STUDENT_COURSE).
                set(STUDENT_COURSE.IS_COURSE_ASSISTANT, true).
                where(STUDENT_COURSE.FK_STUDENT_ID.eq(studentId),
                        STUDENT_COURSE.FK_COURSE_ID.eq(courseId)).
                execute();

        StudentRepository repository = new PostgreSqlStudentRepository(PostgresConnectionFactory.build());
        FindStudentQuery studentQuery = new FindStudentQuery(repository, studentId);

        Student student = studentQuery.execute().get();

        if (!student.getIsCourseAssistant()) {
            sql.update(STUDENT).
                    set(STUDENT.IS_COURSE_ASSISTANT, true).
                    where(STUDENT.STUDENT_ID.eq(studentId)).
                    execute();
        }
    }
}
