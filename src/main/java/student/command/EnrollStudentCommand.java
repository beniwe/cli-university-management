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
    if (student.getId().length() != 8) {
      // TODO: use a custom exception instead.
      throw new IllegalArgumentException("Student ID must be 8 characters long.");
    }

      String hashedPassword;

      try {
          hashedPassword = SHAHash(student.getPassword());
      } catch (NoSuchAlgorithmException e) {
          throw new IllegalStateException(e.getCause());
      }

      Student hashedStudent =
        new Student(
            student.getId(),
            student.getName(),
            student.getBirthDate(),
            student.getEnrolledIn(),
            student.getEnrolledSince(),
            hashedPassword,
            student.getCourseAssistant());

    // TODO: catch and identify duplicate key exception and throw StudentAlreadyEnrolledException
    // instead.
    this.studentRepository.enroll(hashedStudent);
  }

  public static String SHAHash(String password) throws NoSuchAlgorithmException {


      MessageDigest hashFunction = MessageDigest.getInstance("SHA");

      hashFunction.update(password.getBytes());

      byte[] result = hashFunction.digest();
      String hashedPassword = "";

      for (byte currByte : result) {
        hashedPassword += String.format("%02x", currByte);
      }

      return hashedPassword;
  }
}
