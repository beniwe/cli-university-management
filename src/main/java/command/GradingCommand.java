package command;

import student.storage.PostgreSqlStudentRepository;

public class GradingCommand implements Command {
    private PostgreSqlStudentRepository repository;
    private Long studentId;
    private int courseId;
    private int grade;

    public GradingCommand(PostgreSqlStudentRepository repository, Long studentId, int courseId, int grade) {
        this.repository = repository;
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
    }

    @Override
    public void execute() {
        repository.grading(studentId, courseId, grade);
    }
}
