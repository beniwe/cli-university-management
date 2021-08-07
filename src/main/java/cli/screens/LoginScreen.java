package cli.screens;

import java.util.Scanner;

import cli.CliApplication;
import authentication.Authenticate;
import org.jooq.DSLContext;

public class LoginScreen implements Screen {
  private DSLContext sql;

  public LoginScreen(DSLContext sql) {
    this.sql = sql;
  }

  @Override
  public void show(Scanner in) {
    Session session;

    while (true) {
      System.out.print("ID: ");
      Long id = in.nextLong();

      in.nextLine();

      System.out.print("Password: ");
      String password = in.nextLine();

      Authenticate authentication = new Authenticate(sql, id, password);



      try {
         session = authentication.execute();

         break;

      } catch (IllegalArgumentException e) {
        printFailedLoginMessage();

        int choice = in.nextInt();

        if (choice == 1) {}

        else if (choice == 2) {
          return;
        }
      }
    }
    session.show(new Scanner(System.in));
  }

  private void printFailedLoginMessage() {
    System.out.println("\n" + "Invalid ID and/or password");
    System.out.println(CliApplication.sectionString(
            "(1) Try again\n" +
            "(2) Exit"));
  }
}
