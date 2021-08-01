package cli;

import cli.screens.EnrollStudentScreen;
import java.util.Scanner;
import student.storage.StudentRepository;

public class CliApplication implements Runnable {
  private final StudentRepository studentRepository;
  private final EnrollStudentScreen enrollStudentScreen;
  // private final Session userSession;

  public CliApplication(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
    this.enrollStudentScreen = new EnrollStudentScreen(studentRepository);
  }

  void printMenu() {
    System.out.println("Menu");
    System.out.println("---------------");
    System.out.println("1) Enroll a Student");
    System.out.println("2) Exit");
  }

  void exit() {
    System.exit(0);
  }

  @Override
  public void run() {
    try (Scanner in = new Scanner(System.in)) {
      for (; ; ) {
        printMenu();
        var choice = in.nextInt();
        in.nextLine();
        if (choice == 1) {
          this.enrollStudentScreen.show(in);
        } else if (choice == 2) {
          exit();
        } else {
          System.err.println("Invalid input.");
        }
      }
    }
  }
}
