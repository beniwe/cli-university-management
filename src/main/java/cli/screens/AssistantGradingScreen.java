package cli.screens;

import cli.CliApplication;
import command.GradingCommand;
import org.example.models.tables.pojos.Course;
import org.example.models.tables.pojos.Student;
import org.jooq.DSLContext;
import storage.PostgresConnectionFactory;
import student.query.FindCoursesQuery;
import student.query.FindStudentQuery;
import student.storage.PostgreSqlStudentRepository;

import java.util.*;

public class AssistantGradingScreen implements Screen{
    private Student student;

    public AssistantGradingScreen(Student student) {
        this.student = student;
    }


    @Override
    public void show(Scanner in) {
        Integer courseId;
        Long studentId;
        int courseChoice;
        int studentChoice;
        int grade;

        while (true) {
            List<Integer> courseIds = printGradableCourses();

            System.out.print("Choose a course: ");

            DSLContext sql = PostgresConnectionFactory.build();

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

        while (true) {
            List<Long> studentIds = null;

            try {
                studentIds = printNonGradedStudents(courseId);
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                in.nextLine();
                return;
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
                in.nextLine();
                return;
            }

            System.out.print("Choose a student: ");

            try {
                studentChoice = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("(!) Invalid input");

                in.nextLine();
                continue;
            }

            if (studentChoice >= 1 && studentChoice <= studentIds.size()) {
                studentId = studentIds.get(studentChoice - 1);

                break;

            } else {
                System.out.println("(!) Invalid input");

                continue;
            }
        }

        while (true) {

            System.out.print("Grade (1-5): ");

            try {
                grade = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("(!) Invalid input");

                in.nextLine();
                continue;
            }

            if (grade >= 1 && grade <= 5) {
                var repository = new PostgreSqlStudentRepository(PostgresConnectionFactory.build());
                GradingCommand grading = new GradingCommand(repository, studentId, courseId, grade);

                grading.execute();

                in.nextLine();
                return;
            }
        }





    }

    private List<Integer> printGradableCourses() {
        String courses = "";

        List<Integer> courseIds = new ArrayList<>();
        var courseQuery = new FindCoursesQuery(PostgresConnectionFactory.build(), student.getStudentId());
        var courseList = courseQuery.findAssistantCourses();
        int listNumbers = 1;

        for (Course currCourse : courseList) {

            courses += String.format("(%d) %s\n", listNumbers, currCourse.getName());

            courseIds.add(currCourse.getCourseId());

            listNumbers++;
        }

        StringBuilder sb = new StringBuilder(courses);

        sb.deleteCharAt(courses.length() - 1);

        System.out.println("\nGradable Courses:\n" + CliApplication.sectionString(sb.toString()));

        return courseIds;
    }

    private List<Long> printNonGradedStudents(int courseId) {
        String students = "";

        List<Long> studentIds = new ArrayList<>();
        var repository = new PostgreSqlStudentRepository(PostgresConnectionFactory.build());
        var studentQuery = new FindStudentQuery(repository, student.getStudentId());
        var studentList = studentQuery.getNonGradedStudentsInCourse(courseId);
        int listNumbers = 1;

        for (Student currStudent : studentList) {

            students += String.format("(%d) %s\n", listNumbers, currStudent.getName());

            studentIds.add(currStudent.getStudentId());

            listNumbers++;
        }

        StringBuilder sb = new StringBuilder(students);

        sb.deleteCharAt(students.length() - 1);

        System.out.println("\nGradable Students:\n" + CliApplication.sectionString(sb.toString()));

        return studentIds;
    }
}
