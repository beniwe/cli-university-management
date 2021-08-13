package cli.screens.mainScreens;

import authentication.Authenticate;
import cli.CliApplication;
import cli.screens.Screen;
import org.jooq.DSLContext;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginScreen implements Screen {
  private DSLContext sql;

  public LoginScreen(DSLContext sql) {
    this.sql = sql;
  }

  @Override
  public void show(Scanner in) {
    Session session;

    while (true) {

      Long id;

      while (true) {
        System.out.print("ID: ");
        try {
          id = in.nextLong();


          break;
        } catch (InputMismatchException e) {
          System.out.println("\n(!) Input needs to be an integer\n");

          in.nextLine();
        }
      }

      in.nextLine();

      System.out.print("Password: ");
      String password = in.nextLine();

      Authenticate authentication = new Authenticate(sql, id, password);

      try {
        session = authentication.execute();

        break;

      } catch (IllegalArgumentException e) {

        while (true) {
          printFailedLoginMessage();

          String choice = in.nextLine();

          if (choice.equals("1")) {
            break;
          } else if (choice.equals("2")) {
            return;
          }
          else {
            System.out.println("(!) Invalid input");
          }
        }
      }
    }
    session.show(new Scanner(System.in));
  }

  private void printFailedLoginMessage() {
    System.out.println("\n(!) Invalid ID and/or password");
    System.out.println(CliApplication.sectionString("(1) Try again\n" + "(2) Exit"));
  }
}
