package cli;

import cli.screens.LoginScreen;
import cli.screens.RegisterScreen;
import org.jooq.DSLContext;
import student.storage.PostgreSqlStudentRepository;

import java.util.Scanner;

public class CliApplication implements Runnable {
  private final LoginScreen loginScreen;
  private final RegisterScreen registerScreen;

  public CliApplication(DSLContext sql) {
    this.registerScreen = new RegisterScreen(new PostgreSqlStudentRepository(sql));
    this.loginScreen = new LoginScreen(sql);
  }

  void printMenu() {
    System.out.println("\nMenu");
    System.out.println(sectionString("1) Login\n" + "2) Register (students only)\n" + "3) Exit"));
  }

  void exit() {
    System.exit(0);
  }

  @Override
  public void run() {
    try (Scanner in = new Scanner(System.in)) {
      while (true) {
        printMenu();

        String choice = in.nextLine();

        if (choice.equals("1")) {
          loginScreen.show(in);
        } else if (choice.equals("2")) {
          this.registerScreen.show(in);
        } else if (choice.equals("3")) {
          exit();
        } else {
          System.err.println("(!) Invalid input.");
        }

        in.nextLine();
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

      if (currLength > maxLength) {
        maxLength = currLength;
      }

      currLength++;
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
