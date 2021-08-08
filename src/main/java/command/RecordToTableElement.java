package command;

import org.example.models.tables.pojos.Course;
import org.example.models.tables.pojos.DegreeProgram;
import org.example.models.tables.pojos.Professor;
import org.example.models.tables.pojos.Student;
import org.example.models.tables.records.CourseRecord;
import org.example.models.tables.records.DegreeProgramRecord;
import org.example.models.tables.records.ProfessorRecord;
import org.example.models.tables.records.StudentRecord;

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
                record.getAssignedProfessor());
    }
}
