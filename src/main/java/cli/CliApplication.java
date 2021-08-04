package cli;

import cli.screens.RegisterScreen;
import java.util.Scanner;
import student.storage.StudentRepository;

public class CliApplication implements Runnable {
  private final StudentRepository studentRepository;
  private final RegisterScreen registerScreen;

  public CliApplication(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
    this.registerScreen = new RegisterScreen(studentRepository);
  }

  void printMenu() {
    System.out.println("Menu");
    System.out.println(sectionString(
            "1) Login\n" +
            "2) Register (students only)\n" +
            "3) Exit"));
  }

  void exit() {
    System.exit(0);
  }

  @Override
  public void run() {
    try (Scanner in = new Scanner(System.in)) {
      while (true) {
        printMenu();
        var choice = in.nextInt();
        in.nextLine();
        if (choice == 1) {

        } else if (choice == 2) {
          this.registerScreen.show(in);

        } else if (choice == 3) {
          exit();
        } else {
          System.err.println("Invalid input.");
        }
      }
    }
  }

  public static String sectionString(String string) {
    String result = "";
    char[] chars = string.toCharArray();
    int maxLength = 0;
    int currLength = 0;

    for (char currChar : chars) {

      if (currLength > maxLength) {
        maxLength = currLength;
      }

      if (currChar != '\n') {
        currLength++;

      } else {
        currLength = 0;
      }
    }

    for (int i = 0; i < maxLength; i++) {
      result += "-";
    }

    result += "\n" + string + "\n";

    for (int i = 0; i < maxLength; i++) {
      result += "-";
    }

    return result;
  }
}
