package cli.screens;

import cli.CliApplication;
import org.example.models.tables.pojos.Course;
import org.example.models.tables.pojos.DegreeProgram;
import org.example.models.tables.pojos.Professor;
import org.example.models.tables.pojos.Student;
import storage.PostgresConnectionFactory;
import student.NoGradeException;
import student.query.FindCoursesQuery;
import student.query.FindDegreeProgramQuery;
import student.storage.PostgreSqlStudentRepository;
import student.storage.StudentRepository;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Session implements Screen {
  private Professor professor;
  private Student student;

  public Session(Professor professor) {
    this.professor = professor;
  }

  public Session(Student student) {
    this.student = student;
  }

  @Override
  public void show(Scanner in) {
    if (professor != null) {
      this.professorSession(in);
    } else if (student != null) {
      this.studentSession(in);
    }
  }

  private void professorSession(Scanner in) {
    System.out.println("Hello " + professor.getName());

    while(true) {
      printProfessorMenu();

      String choice = in.nextLine();

      if (choice.equals("1")) {
        printAccountDetails();
      }

      else if (choice.equals("2")) {

      }

      else if (choice.equals("3")){

      }

      else if (choice.equals("4")) {

      }

      else if (choice.equals("5")) {

      }

      else if (choice.equals("6")) {
        return;
      }

      else if (professor.getIsAdmin() && choice.equals("7")) {

      }

      else if (professor.getIsAdmin() && choice.equals("8")) {

      }

      else if (professor.getIsAdmin() && choice.equals("9")) {

      }

      else {
        System.out.println("(!) Invalid input");
      }
    }
  }

  private void studentSession(Scanner in) {
    System.out.println("Hello " + student.getName());

    while(true) {
      printStudentMenu();

      String choice = in.nextLine();

      if (choice.equals("1")) {
        printAccountDetails();
      }

      else if (choice.equals("2")) {
        try {
          printCurrentCourses();
        } catch (NoSuchElementException e) {
          System.out.println(e.getMessage());
        }
      }

      else if (choice.equals("3")){
        CourseEnrollScreen enrollScreen = new CourseEnrollScreen(student);
        enrollScreen.show(in);
      }

      else if (choice.equals("4")) {
        StudentRepository repository = new PostgreSqlStudentRepository(PostgresConnectionFactory.build());

        repository.remove(student.getStudentId());
        System.out.println("(!) You are now no longer in the System");

        return;
      }

      else if (choice.equals("5")) {
        return;
      }

      else if (student.getIsCourseAssistant() && choice.equals("6")) {
        AssistantGradingScreen assistantGrading = new AssistantGradingScreen(student);
        assistantGrading.show(in);
      }

      else if (student.getIsCourseAssistant() && choice.equals("7")) {

      }

      else {
        System.out.println("(!) Invalid input");
      }
    }
  }

  private void printProfessorMenu() {
    String menu = "";

    if (professor.getIsAdmin() == true) {
      menu +=
          "(1) Account Details\n"
              // display courses, assign CA, grade and remove students
              + "(2) Course Management\n"
              + "(3) Assign Course Assistant\n"
              + "(4) Occupy a Course\n"
              + "(5) Delete Account\n"
              + "(6) Exit\n\n"

              + "Admin Options:\n"
              + "(7) Register Professor\n"
              + "(8) Assign Admin\n"
              + "(9) Remove Professor";



    } else {
      menu +=
          "(1) Account Details\n"
              + "(2) Course Management\n"
              + "(3) Assign Course Assistant\n"
              + "(4) Occupy a Course\n"
              + "(5) Delete Account\n"
              + "(6) Exit";
    }

    System.out.println("\nMenu:\n" + CliApplication.sectionString(menu));
  }

  public void printStudentMenu() {
    String menu = "";

    if (student.getIsCourseAssistant() == true) {
      menu +=
          "(1) Account Details\n"
              + "(2) Display current Courses\n"
              + "(3) Enroll in a Course\n"
              + "(4) Exmatriculate\n"
              + "(5) Exit\n\n"

              + "Course-Assistant Options:\n"
              + "(6) Grade Student\n"
              + "(7) Remove Student";
    } else {
      menu +=
          "(1) Account Details\n"
              + "(2) Display current Courses\n"
              + "(3) Enroll in a Course\n"
              + "(4) Exmatriculate\n"
              + "(5) Exit";
    }

    System.out.println("\nMenu:\n" + CliApplication.sectionString(menu));
  }

  private void printAccountDetails() {
    String accountDetails = "";

    if (professor != null) {
      accountDetails +=
          "ID: " + professor.getProfessorId() + "\n"
                  + "Name: " + professor.getName() + "\n"
                  + "Birth Date: " + professor.getBirthDate() + "\n"
                  + "Admin: " + professor.getIsAdmin();
    }

    if (student != null) {
      var programQuery = new FindDegreeProgramQuery(PostgresConnectionFactory.build(), student.getEnrolledIn());

      DegreeProgram degreeProgramm = programQuery.execute().get();
      String degreeProgrammName = degreeProgramm.getName();

      accountDetails +=
              "ID: " + student.getStudentId() + "\n"
                      + "Name: " + student.getName() + "\n"
                      + "Birth Date: " + student.getBirthDate() + "\n"
                      + "Enrolled in: " + degreeProgrammName + "\n"
                      + "Enrolled since: " + student.getEnrolledSince() + "\n"
                      + "Course Assistant: " + student.getIsCourseAssistant();
    }

    System.out.println("\nAccount Details:\n" + CliApplication.sectionString(accountDetails));
  }

  private void printCurrentCourses() {
    String courses = "";

    var courseQuery = new FindCoursesQuery(PostgresConnectionFactory.build(), student.getStudentId());
    var courseList = courseQuery.execute();

    for (Course currCourse : courseList) {

      try {

        if (currCourse.getAssignedProfessor() != null) {
          courses += String.format("%s | ECTS(%2.1f) | Professor(%s)", currCourse.getName(), currCourse.getEcts(), currCourse.getAssignedProfessor());
        }

        else {
          courses += String.format("%s | ECTS(%2.1f) | Professor(%s)", currCourse.getName(), currCourse.getEcts(), "Unknown");
        }

        int currGrade = courseQuery.getGrade(currCourse.getCourseId());

        courses += " | Grade(" + currGrade + ")\n";

      } catch(NoGradeException e) {

        courses += "\n";
      }
    }

    StringBuilder sb = new StringBuilder(courses);

    sb.deleteCharAt(courses.length() - 1);

    System.out.println("\nCourses:\n" + CliApplication.sectionString(sb.toString()));
  }
}
