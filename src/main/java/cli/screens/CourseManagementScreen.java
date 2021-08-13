package cli.screens;

import cli.CliApplication;
import cli.screens.assistantScreens.AssistantGradingScreen;
import org.example.models.tables.pojos.Course;
import org.example.models.tables.pojos.Professor;
import org.example.models.tables.pojos.Student;
import professor.query.ProfessorCourseQuery;
import storage.PostgresConnectionFactory;
import student.query.FindStudentQuery;
import student.storage.PostgreSqlStudentRepository;

import java.util.*;

public class CourseManagementScreen implements Screen{
    private Professor professor;

    public CourseManagementScreen(Professor professor) {
        this.professor = professor;
    }

    @Override
    public void show(Scanner in) {
        Integer courseId;
        Long studentId;
        int courseChoice;
        int optionChoice;
        int studentChoice;
        int grade;

        while (true) {
            List<Integer> courseIds = printProfessorCourses();

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

        while (true) {
            var gradingMethods = new AssistantGradingScreen(null);

            printOptions();

            try {
                optionChoice = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("(!) Invalid input");

                in.nextLine();
                continue;
            }

            if (optionChoice == 1) {
                //grade
                try {
                    gradingMethods.cliGrading(in, courseId);
                } catch (NoSuchElementException e) {
                    System.out.println(e.getMessage());
                    in.nextLine();
                    return;
                } catch (IllegalStateException e) {
                    System.out.println("(!) Every student in this course has already been graded");
                    in.nextLine();
                    return;
                }
            }

            if (optionChoice == 2) {
                //assign course assistant

                while (true) {
                    List<Long> studentIds = null;

                    try {
                        studentIds = printNonAssistantStudents(courseId);
                    } catch (NoSuchElementException e) {
                        System.out.println(e.getMessage());

                        in.nextLine();
                        return;
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());

                        in.nextLine();
                        return;
                    }


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
            }

            if (optionChoice == 3) {
                //remove
            }

            if (optionChoice == 4) {
                return;
            }

            else {
                System.out.println("(!) Invalid input");

                continue;
            }
        }



    }

    public List<Integer> printProfessorCourses() {
        String courses = "";

        List<Integer> courseIds = new ArrayList<>();
        var courseQuery = new ProfessorCourseQuery(PostgresConnectionFactory.build(), professor.getProfessorId());
        var courseList = courseQuery.execute();
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

    public void printOptions() {
        String options = "(1) Grade a student\n" +
                "(2) Assign Course Assistant\n" +
                "(3) Remove a student\n" +
                "(4) Exit";


        System.out.println("\nChose:\n" + CliApplication.sectionString(options));
    }

    private List<Long> printNonAssistantStudents(int courseId){
        String students = "";

        List<Long> studentIds = new ArrayList<>();
        var repository = new PostgreSqlStudentRepository(PostgresConnectionFactory.build());
        FindStudentQuery studentQuery;

        studentQuery = new FindStudentQuery(repository, null);

        var studentList = studentQuery.getNonAssistantStudent(courseId);
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
}
