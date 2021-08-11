package student.command;

import command.Command;
import org.example.models.tables.pojos.Course;
import org.example.models.tables.pojos.Student;
import student.storage.PostgreSqlStudentRepository;

public class EnrollInCourseCommand implements Command {
    private PostgreSqlStudentRepository repository;
    private Student student;
    private Course course;

    public EnrollInCourseCommand(PostgreSqlStudentRepository repository, Student student, Course course) {
        this.repository = repository;
        this.student = student;
        this.course = course;
    }


    @Override
    public void execute() {
        repository.addStudentToCourse(student, course);
    }
}
