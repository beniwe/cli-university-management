package command;

import org.example.models.tables.pojos.*;
import org.example.models.tables.records.*;

public class RecordToTableElement {
    private RecordToTableElement() {}

    public static Professor recordToProfessor(ProfessorRecord record) {
        return new org.example.models.tables.pojos.Professor(
                record.getProfessorId(),
                record.getName(),
                record.getBirthDate(),
                record.getPassword(),
                record.getIsAdmin());
    }

    public static Student recordToStudent(StudentRecord record) {
        return new Student(
                record.getStudentId(),
                record.getName(),
                record.getBirthDate(),
                record.getEnrolledIn(),
                record.getEnrolledSince(),
                record.getPassword(),
                record.getIsCourseAssistant());
    }

    public static DegreeProgram recordToProgram(DegreeProgramRecord record) {
        return new DegreeProgram(
                record.getProgramId(),
                record.getName());
    }

    public static Course recordToCourse(CourseRecord record) {
        return new Course(
                record.getCourseId(),
                record.getName(),
                record.getEcts(),
                record.getAssignedProfessor());
    }

    public static StudentCourse recordToStudentCourse(StudentCourseRecord record) {
        return new StudentCourse(record.getFkStudentId(),
                record.getFkCourseId(),
                record.getIsCourseAssistant(),
                record.getGrade());
    }
}
