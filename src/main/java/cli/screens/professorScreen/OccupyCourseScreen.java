package cli.screens.professorScreen;

import cli.CliApplication;
import cli.screens.Screen;
import org.example.models.tables.pojos.Course;
import org.example.models.tables.pojos.Professor;
import professor.command.OccupyCourseCommand;
import professor.query.ProfessorCourseQuery;
import storage.PostgresConnectionFactory;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class OccupyCourseScreen implements Screen {
    private Professor professor;

    public OccupyCourseScreen(Professor professor) {
        this.professor = professor;
    }

    @Override
    public void show(Scanner in) {
        int courseChoice;
        int courseId;

        while (true) {
            List<Integer> courseIds = printNonOccupiedCourses();

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

        var occupyCourse = new OccupyCourseCommand(PostgresConnectionFactory.build(), professor.getProfessorId(), courseId);

        occupyCourse.execute();

        in.nextLine();
        return;
    }

    public List<Integer> printNonOccupiedCourses() {
        String courses = "";

        List<Integer> courseIds = new ArrayList<>();
        var courseQuery = new ProfessorCourseQuery(PostgresConnectionFactory.build(), professor.getProfessorId());
        var courseList = courseQuery.getNonOccupiedCourses();
        int listNumbers = 1;

        for (Course currCourse : courseList) {

            courses += String.format("(%d) %s\n", listNumbers, currCourse.getName());

            courseIds.add(currCourse.getCourseId());

            listNumbers++;
        }

        StringBuilder sb = new StringBuilder(courses);

        sb.deleteCharAt(courses.length() - 1);

        System.out.println("\nOpen Courses:\n" + CliApplication.sectionString(sb.toString()));

        return courseIds;
    }
}
