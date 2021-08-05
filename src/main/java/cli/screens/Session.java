package cli.screens;

import java.util.Scanner;

import authentication.User;
import org.example.models.tables.pojos.Professor;
import org.example.models.tables.pojos.Student;

public class Session implements Screen {
  private User user;

  public Session(User user) {
    this.user = user;
  }

  @Override
  public void show(Scanner in) {
    if (user.getProfessor() != null) {
      this.professorSession(in, user.getProfessor());
    }

    else if (user.getStudent() != null){
      this.studentSession(in, user.getStudent());
    }
  }

  public void professorSession(Scanner in, Professor professor) {}

  public void studentSession(Scanner in, Student student) {}
}
