package student.command;

import command.Command;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.example.models.tables.pojos.Student;
import student.storage.StudentRepository;

public class EnrollStudentCommand implements Command {
  private final StudentRepository studentRepository;
  private final Student student;

  public EnrollStudentCommand(StudentRepository studentRepository, Student student) {
    this.studentRepository = studentRepository;
    this.student = student;
  }

  @Override
  public void execute() {
    String hashedPassword;

    hashedPassword = SHAHash(student.getPassword());

    Student hashedStudent =
        new Student(
            student.getStudentId(),
            student.getName(),
            student.getBirthDate(),
            student.getEnrolledIn(),
            student.getEnrolledSince(),
            hashedPassword,
            student.getIsCourseAssistant());

    // TODO: catch and identify duplicate key exception and throw StudentAlreadyEnrolledException
    // instead.
    this.studentRepository.enroll(hashedStudent);
  }

  public static String SHAHash(String password) {

    MessageDigest hashFunction;
    try {
      hashFunction = MessageDigest.getInstance("SHA");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e.getCause());
    }

    hashFunction.update(password.getBytes());

    byte[] result = hashFunction.digest();
    String hashedPassword = "";

    for (byte currByte : result) {
      hashedPassword += String.format("%02x", currByte);
    }

    return hashedPassword;
  }
}
