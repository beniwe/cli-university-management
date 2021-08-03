package command;

import java.security.NoSuchAlgorithmException;

public class LoginCheck implements Command{

    @Override

    public void execute() {
        try {

        } catch (IllegalArgumentException e) {
            System.out.println("either given ID doesn't exist, given password is invalid or both \fTry again");
        }
    }
}
