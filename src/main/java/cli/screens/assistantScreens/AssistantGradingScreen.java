package cli.screens.assistantScreens;

import cli.CliApplication;
import cli.screens.Screen;
import command.GradingCommand;
import org.example.models.tables.pojos.Course;
import org.example.models.tables.pojos.Student;
import storage.PostgresConnectionFactory;
import student.query.FindCoursesQuery;
import student.query.FindStudentQuery;
import student.storage.PostgreSqlStudentRepository;

import java.util.*;

public class AssistantGradingScreen implements Screen {
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
        try {
            cliGrading(in, courseId);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            in.nextLine();
            return;
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            in.nextLine();
            return;
        }
    }

    public List<Integer> printGradableCourses() {
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

        System.out.println("\nChoose Course:\n" + CliApplication.sectionString(sb.toString()));

        return courseIds;
    }

    public List<Long> printNonGradedStudents(int courseId) {
        String students = "";

        List<Long> studentIds = new ArrayList<>();
        var repository = new PostgreSqlStudentRepository(PostgresConnectionFactory.build());
        FindStudentQuery studentQuery;

        if (student == null) {
            studentQuery = new FindStudentQuery(repository, null);
        }

        else {
            studentQuery = new FindStudentQuery(repository, student.getStudentId());
        }

        var studentList = studentQuery.getNonGradedStudentsInCourse(courseId);
        int listNumbers = 1;

        for (Student currStudent : studentList) {

            students += String.format("(%d) %s | ID: %s\n", listNumbers, currStudent.getName(), currStudent.getStudentId());

            studentIds.add(currStudent.getStudentId());

            listNumbers++;
        }

        StringBuilder sb = new StringBuilder(students);

        sb.deleteCharAt(students.length() - 1);

        System.out.println("\nChoose Student:\n" + CliApplication.sectionString(sb.toString()));

        return studentIds;
    }

    public void cliGrading(Scanner in, int courseId) {
        Long studentId;

        while (true) {
            int studentChoice;
            List<Long> studentIds;

            studentIds = printNonGradedStudents(courseId);

            System.out.print("Student: ");

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
            int grade;

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
}
