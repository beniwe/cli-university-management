package cli.screens;

import java.util.Scanner;

import authentication.User;
import cli.CliApplication;
import org.example.models.tables.pojos.Professor;
import org.example.models.tables.pojos.Student;
import org.w3c.dom.ls.LSOutput;

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
    }

    else if (student != null){
      this.studentSession(in);
    }
  }

  public void professorSession(Scanner in) {
    System.out.println("Hello " + professor.getName() + "\n");

    printProfessorMenu();

    in.nextLine();
  }

  public void studentSession(Scanner in) {
    System.out.println("Hello " + student.getName());

    printStudentMenu();

    in.nextLine();
  }

  public void printProfessorMenu() {
    String menu = "";

    if (professor.getIsAdmin() == true) {
      menu += "(1) Account Details\n" +
              //display courses, assign CA, grade and remove students
              "(2) Course Management\n" +
              "(3) Register Professor\n" +
              "(4) Assign Admin\n" +
              "(5) Occupy a Course\n" +
              "(6) Delete Account";
    }

    else {
      menu += "(1) Account Details\n" +
              "(2) Course Management\n" +
              "(3) Assign Course Assistant\n" +
              "(4) Occupy a Course\n" +
              "(5) Delete Account";
    }

    System.out.println("\nMenu:\n" + CliApplication.sectionString(menu));
  }

  public void printStudentMenu() {
    String menu = "";

    if (student.getIsCourseAssistant() == true) {
      menu += "(1) Account Details\n" +
              "(2) Display current Courses\n" +
              "(3) Grade Student\n" +
              "(4) Enroll in a Course\n" +
              "(5) Exmatriculate";
    }

    else {
      menu += "(1) Account Details\n" +
              "(2) Display current Courses\n" +
              "(3) Enroll in a Course\n" +
              "(4) Exmatriculate";
    }

    System.out.println("\nMenu:\n" + CliApplication.sectionString(menu));
  }
}
