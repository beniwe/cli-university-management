package query;

import static org.example.models.Tables.*;

import java.util.ArrayList;
import java.util.List;
import org.example.models.tables.records.CourseRecord;
import org.example.models.tables.records.DegreeProgramRecord;
import org.jooq.DSLContext;

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

  public static List<String> courseTable(DSLContext sql) {

    List<String> courses = new ArrayList<>();

    var courseRecords = sql.fetch(COURSE);

    for (CourseRecord currCourse : courseRecords) {
      courses.add("(" + currCourse.getCourseId() + ")" + currCourse.getName());
    }

    return courses;
  }
}
