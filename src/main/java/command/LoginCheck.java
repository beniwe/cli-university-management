package command;

public class LoginCheck implements Command {

  @Override
  public void execute() {
    try {

    } catch (IllegalArgumentException e) {
      System.out.println(
          "either given ID doesn't exist, given password is invalid or both \nTry again");
    }
  }
}
