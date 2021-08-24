package cli.screens.assistantScreens;

import cli.CliApplication;
import cli.screens.Screen;
import org.example.models.tables.pojos.Course;
import org.example.models.tables.pojos.Student;
import storage.PostgresConnectionFactory;
import student.query.FindCoursesQuery;
import student.query.FindStudentQuery;
import student.storage.PostgreSqlStudentRepository;

import java.util.*;

public class AssistantRemoveScreen implements Screen {
    private Student student;

    public AssistantRemoveScreen(Student student) {
        this.student = student;
    }

    @Override
    public void show(Scanner in) {
        var getCourses = new AssistantGradingScreen(student);
        var repository = new PostgreSqlStudentRepository(PostgresConnectionFactory.build());


        Integer courseId;
        int courseChoice;

        while (true) {
            List<Integer> courseIds = getCourses.printGradableCourses();

            System.out.print("Choose a course: ");

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
            cliRemoving(in, courseId);
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

    public List<Long> printStudentsInCourse(int courseId) {
        String students = "";

        List<Long> studentIds = new ArrayList<>();
        var repository = new PostgreSqlStudentRepository(PostgresConnectionFactory.build());
        FindStudentQuery studentQuery;

        if (student != null) {
            studentQuery = new FindStudentQuery(repository, student.getStudentId());
        }

        else {
            studentQuery = new FindStudentQuery(repository, null);
        }

        var studentList = studentQuery.getStudentsInCourse(courseId);
        int listNumbers = 1;

        for (Student currStudent : studentList) {

            students += String.format("(%d) %s | ID: %d\n", listNumbers, currStudent.getName(), currStudent.getStudentId());

            studentIds.add(currStudent.getStudentId());

            listNumbers++;
        }

        StringBuilder sb = new StringBuilder(students);

        sb.deleteCharAt(students.length() - 1);

        System.out.println("\nChose:\n" + CliApplication.sectionString(sb.toString()));

        return studentIds;
    }

    public void cliRemoving(Scanner in, int courseId) {
        Long studentId;
        int studentChoice;
        var repository = new PostgreSqlStudentRepository(PostgresConnectionFactory.build());

        while (true) {
            List<Long> studentIds;

            studentIds = printStudentsInCourse(courseId);

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

        repository.removeFromCourse(studentId, courseId);

        FindStudentQuery studentQuery = new FindStudentQuery(repository, studentId);
        Student student = studentQuery.execute().get();

        FindCoursesQuery coursesQuery = new FindCoursesQuery(PostgresConnectionFactory.build(), studentId);
        Course course = coursesQuery.findCourseById(courseId).get();


        System.out.println("Student " + student.getName() + " has been removed from course: " + course.getName());

        in.nextLine();
        return;
    }


}
