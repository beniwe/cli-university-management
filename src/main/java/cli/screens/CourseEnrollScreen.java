package cli.screens;

import cli.CliApplication;
import org.example.models.tables.pojos.Course;
import org.example.models.tables.pojos.Student;
import org.jooq.DSLContext;
import storage.PostgresConnectionFactory;
import student.command.EnrollInCourseCommand;
import student.query.FindCoursesQuery;
import student.storage.PostgreSqlStudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseEnrollScreen implements Screen{
    private Student student;

    public CourseEnrollScreen(Student student) {
        this.student = student;
    }

    @Override
    public void show(Scanner in) {

        while (true) {
            List<String> courseIds = printOpenCourses();

            System.out.print("Course ID: ");

            DSLContext sql = PostgresConnectionFactory.build();
            var repository = new PostgreSqlStudentRepository(sql);


            String choice = in.nextLine();

            for (String currCourseId : courseIds) {
                if (currCourseId.equals(choice)) {
                    FindCoursesQuery getCourse = new FindCoursesQuery(sql, null);
                    Course course = getCourse.findCourseById(Integer.parseInt(currCourseId)).get();

                    EnrollInCourseCommand enroll = new EnrollInCourseCommand(repository, student, course);
                    enroll.execute();

                    return;
                }
            }

            System.out.println("invalid input");
        }
    }

    public List<String> printOpenCourses() {
        String courses = "";

        List<String> courseIds = new ArrayList<>();
        var courseQuery = new FindCoursesQuery(PostgresConnectionFactory.build(), student.getStudentId());
        var courseList = courseQuery.notEnrolledInCourses();

        for (Course currCourse : courseList) {

            courses += String.format("(%d) %s | ECTS(%2.1f)\n", currCourse.getCourseId(), currCourse.getName(), currCourse.getEcts());

            courseIds.add("" + currCourse.getCourseId());
        }

        StringBuilder sb = new StringBuilder(courses);

        sb.deleteCharAt(courses.length() - 1);

        System.out.println("\nOpen-Courses:\n" + CliApplication.sectionString(sb.toString()));

        return courseIds;
    }
}
