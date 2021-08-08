package authentication;

import cli.screens.Session;
import org.example.models.tables.pojos.Professor;
import org.example.models.tables.pojos.Student;
import org.jooq.DSLContext;
import professor.PostgreSqlProfessorRepository;
import student.command.EnrollStudentCommand;
import student.storage.PostgreSqlStudentRepository;

public class Authenticate {
  private DSLContext sql;
  private Long id;
  private String hashedPassword;

  public Authenticate(DSLContext sql, Long id, String password) {
    this.sql = sql;
    this.id = id;
    this.hashedPassword = EnrollStudentCommand.SHAHash(password);
  }

  public Session execute() {
    Professor professor;
    Student student;
    Session session;

    if (id < 10000000) {
      professor = new Professor(professorLoginCheck(id, hashedPassword));

      session = new Session(professor);
    } else {
      student = new Student(studentLoginCheck(id, hashedPassword));

      session = new Session(student);
    }

    return session;
  }

  public Professor professorLoginCheck(Long id, String hashedPassword) {
    var professorRepo = new PostgreSqlProfessorRepository(sql);
    var maybeProfessor = professorRepo.findProfessorById(id);

    if (maybeProfessor.isEmpty()) {
      throw new IllegalArgumentException("wrong ID");
    }

    var professor = maybeProfessor.get();

    if (!professor.getPassword().equals(hashedPassword)) {
      throw new IllegalArgumentException("wrong Password");
    }

    return professor;
  }

  public Student studentLoginCheck(Long id, String hashedPassword) {
    var studentRepo = new PostgreSqlStudentRepository(sql);
    var maybeStudent = studentRepo.findStudentById(id);

    if (maybeStudent.isEmpty()) {
      throw new IllegalArgumentException("wrong ID");
    }

    var student = maybeStudent.get();

    if (!student.getPassword().equals(hashedPassword)) {
      throw new IllegalArgumentException("wrong Password");
    }

    return student;
  }
}
