package cli;

import cli.screens.RegisterScreen;
import java.util.Scanner;
import student.storage.StudentRepository;

public class CliApplication implements Runnable {
  private final StudentRepository studentRepository;
  private final RegisterScreen registerScreen;
  // private final Session userSession;

  public CliApplication(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
    this.registerScreen = new RegisterScreen(studentRepository);
  }

  void printMenu() {
    System.out.println("Menu");
    System.out.println("---------------");
    System.out.println("1) Login");
    System.out.println("2) Register (student only)");
    System.out.println("3) Exit");
  }

  void exit() {
    System.exit(0);
  }

  @Override
  public void run() {
    try (Scanner in = new Scanner(System.in)) {
      for (;;) {
        printMenu();
        var choice = in.nextInt();
        in.nextLine();
        if (choice == 1) {
          this.registerScreen.show(in);
        } else if (choice == 2) {
          exit();
        } else if (choice == 3) {

        } else {
          System.err.println("Invalid input.");
        }
      }
    }
  }
}
