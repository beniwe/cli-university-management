package cli;

import cli.screens.LoginScreen;
import cli.screens.RegisterScreen;
import java.util.Scanner;

import org.jooq.DSLContext;
import student.storage.PostgreSqlStudentRepository;
import student.storage.StudentRepository;

public class CliApplication implements Runnable {
  private final LoginScreen loginScreen;
  private final RegisterScreen registerScreen;

  public CliApplication(DSLContext sql) {
    this.registerScreen = new RegisterScreen(new PostgreSqlStudentRepository(sql));
    this.loginScreen = new LoginScreen(sql);
  }

  void printMenu() {
    System.out.println("\nMenu");
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
          loginScreen.show(in);

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

      if (currChar == '\n') {
        currLength = 0;
      }

      currLength++;

      if (currLength > maxLength) {
        maxLength = currLength;
      }
    }

    for (int i = 0; i < maxLength; i++) {
      result += "-";
    }

    result += "\n" + string + "\n";

    for (int i = 0; i < maxLength; i++) {
      result += "-";
    }

    return result + "\n";
  }
}
