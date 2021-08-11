package query;

import command.RecordToTableElement;
import org.example.models.tables.records.CourseRecord;
import org.example.models.tables.records.DegreeProgramRecord;
import org.jooq.DSLContext;
import org.example.models.tables.pojos.Course;

import java.util.ArrayList;
import java.util.List;

import static org.example.models.Tables.COURSE;
import static org.example.models.Tables.DEGREE_PROGRAM;

public class GetTableQuery {
  private GetTableQuery() {}

  public static List<String> degreeProgramTable(DSLContext sql) {

    List<String> programs = new ArrayList<>();

    var degreeProgramRecords = sql.fetch(DEGREE_PROGRAM);

    for (DegreeProgramRecord currProgram : degreeProgramRecords) {
      programs.add("(" + currProgram.getProgramId() + ") " + currProgram.getName());
    }

    return programs;
  }

  public static List<Course> courseTable(DSLContext sql) {

    List<Course> courses = new ArrayList<>();

    var courseRecords = sql.fetch(COURSE);

    for (CourseRecord currCourseRecord : courseRecords) {
      courses.add(RecordToTableElement.recordToCourse(currCourseRecord));
    }

    return courses;
  }
}
