package student.command;

import command.Command;
import student.storage.PostgreSqlStudentRepository;

public class RemoveFromCourseCommand implements Command {
    private PostgreSqlStudentRepository repository;
    private Long studentId;
    private int courseId;

    public RemoveFromCourseCommand(PostgreSqlStudentRepository repository, Long studentId, int courseId) {
        this.repository = repository;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    @Override
    public void execute() {
        repository.removeFromCourse(studentId, courseId);
    }
}
