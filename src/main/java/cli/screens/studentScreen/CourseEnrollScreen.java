package cli.screens.studentScreen;

import cli.CliApplication;
import cli.screens.Screen;
import org.example.models.tables.pojos.Course;
import org.example.models.tables.pojos.Student;
import storage.PostgresConnectionFactory;
import student.command.EnrollInCourseCommand;
import student.query.FindCoursesQuery;
import student.storage.PostgreSqlStudentRepository;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CourseEnrollScreen implements Screen {
    private Student student;

    public CourseEnrollScreen(Student student) {
        this.student = student;
    }

    @Override
    public void show(Scanner in) {
    int courseChoice;
    int courseId;


        while (true) {
            List<Integer> courseIds = printOpenCourses();

            System.out.print("Course: ");

            try {
                courseChoice = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("(!) Invalid input");

                in.nextLine();
                continue;
            }

            if (courseChoice >= 1 && courseChoice <= courseIds.size()) {
                courseId = courseIds.get(courseChoice - 1);

                break;
            } else {
                System.out.println("(!) Invalid input");

                continue;
            }
        }
        var sql = PostgresConnectionFactory.build();
        var repository = new PostgreSqlStudentRepository(sql);

        FindCoursesQuery getCourse = new FindCoursesQuery(sql, null);
        Course course = getCourse.findCourseById(courseId).get();

        EnrollInCourseCommand enroll = new EnrollInCourseCommand(repository, student, course);
        enroll.execute();

        in.nextLine();
        return;
    }

    private List<Integer> printOpenCourses() {
        String courses = "";

        List<Integer> courseIds = new ArrayList<>();
        var courseQuery = new FindCoursesQuery(PostgresConnectionFactory.build(), student.getStudentId());
        var courseList = courseQuery.notEnrolledInCourses();
        var serial = 1;

        for (Course currCourse : courseList) {

            courses += String.format("(%d) %s | ECTS(%2.1f)\n", serial++, currCourse.getName(), currCourse.getEcts());

            courseIds.add(currCourse.getCourseId());
        }

        StringBuilder sb = new StringBuilder(courses);

        sb.deleteCharAt(courses.length() - 1);

        System.out.println("\nOpen-Courses:\n" + CliApplication.sectionString(sb.toString()));

        return courseIds;
    }
}
