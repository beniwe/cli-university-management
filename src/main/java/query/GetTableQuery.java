package query;

import static org.example.models.Tables.*;

import org.example.models.tables.records.CourseRecord;
import org.example.models.tables.records.DegreeProgramRecord;
import org.jooq.DSLContext;

public class GetTableQuery {
  private GetTableQuery() {}
  ;

  public static String degreeProgrammTable(DSLContext sql) {
    String result = "";

    var degreePrograms = sql.fetch(DEGREE_PROGRAM);

    for (DegreeProgramRecord currProgram : degreePrograms) {
      result += "(" + currProgram.getProgramId() + ") " + currProgram.getName() + "\n";
    }

    StringBuilder sb = new StringBuilder(result);

    sb.deleteCharAt(result.length() - 1);

    return sb.toString();
  }

  public static String courseTable(DSLContext sql) {
    String result = "";

    var courses = sql.fetch(COURSE);

    for (CourseRecord currCourse : courses) {
      result += "(" + currCourse.getCourseId() + ") " + currCourse.getName() + "\n";
    }

    StringBuilder sb = new StringBuilder(result);

    sb.deleteCharAt(result.length() - 1);

    return sb.toString();
  }
}
