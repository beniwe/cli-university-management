package cli.screens;

import java.util.Scanner;

import cli.CliApplication;
import authentication.LoginCheck;
import authentication.User;
import org.jooq.DSLContext;

public class LoginScreen implements Screen {
  private DSLContext sql;

  public LoginScreen(DSLContext sql) {
    this.sql = sql;
  }

  @Override
  public void show(Scanner in) {
    User user = null;

    while (true) {
      System.out.print("ID: ");
      Long id = in.nextLong();

      in.nextLine();

      System.out.print("Password: ");
      String password = in.nextLine();

      LoginCheck authentication = new LoginCheck(sql, id, password);



      try {
        user = authentication.execute();

        break;

      } catch (IllegalArgumentException e) {
        loginFailMessage();

        int choice = in.nextInt();

        if (choice == 1) {}

        else if (choice == 2) {
          return;
        }
      }
    }
    Session session = new Session(user);
  }

  private void loginFailMessage() {
    System.out.println("\n" + "Invalid ID and/or password");
    System.out.println(CliApplication.sectionString(
            "(1) Try again\n" +
            "(2) Exit"));
  }
}
