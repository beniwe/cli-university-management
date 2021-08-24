package cli;

import cli.screens.mainScreens.LoginScreen;
import cli.screens.mainScreens.StudentRegisterScreen;
import org.jooq.DSLContext;
import student.storage.PostgreSqlStudentRepository;

import java.util.Scanner;

public class CliApplication implements Runnable {
  private final LoginScreen loginScreen;
  private final StudentRegisterScreen studentRegisterScreen;

  public CliApplication(DSLContext sql) {
    this.studentRegisterScreen = new StudentRegisterScreen(new PostgreSqlStudentRepository(sql));
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
      String choice = null;

      while (true) {
        if (choice != "") {
          printMenu();
        }

        choice = in.nextLine();

        if (choice.equals("1")) {
          loginScreen.show(in);
        } else if (choice.equals("2")) {
          this.studentRegisterScreen.show(in);
        } else if (choice.equals("3")) {
          exit();
        } else if (choice.equals("")) {
          continue;
        } else {
          System.err.println("(!) Invalid input.");
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
