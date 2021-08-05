package student.storage;

import student.StudentAlreadyEnrolledException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.models.tables.pojos.Student;

public class InMemoryStudentRepository implements StudentRepository {
  private final List<Student> students;

  public InMemoryStudentRepository() {
    this.students = new ArrayList<>();
  }

  @Override
  public Optional<Student> findStudentById(Long id) {
    return students.stream().filter(student -> student.getStudentId().equals(id)).findFirst();
  }

  public Student studentLoginCheck(Long id, String password) {
    var maybeStudent = findStudentById(id);

    if (maybeStudent.isEmpty()) {
      throw new IllegalArgumentException("wrong ID");
    }

    Student searchedStudent = maybeStudent.get();

    if (!searchedStudent.getPassword().equals(password)) {
      throw new IllegalArgumentException("wrong password");
    }

    return searchedStudent;
  }

  @Override
  public Student enroll(Student student) throws StudentAlreadyEnrolledException {
    for (Student currStudent : students) {
      if (currStudent.getStudentId().equals(student.getStudentId())) {
        throw new StudentAlreadyEnrolledException("ID already exists");
      }
    }
    this.students.add(student);

    System.out.println("Your ID is: " + getMaxStudentId());

    return student;
  }

  @Override
  public Student remove(Long id) {

    for (Student currentStudent : students) {
      if (currentStudent.getStudentId().equals(id)) {
        students.remove(currentStudent);

        return currentStudent;
      }
    }
    return null;
  }


  private Long getMaxStudentId() {
    Long maxId = students.get(0).getStudentId();

    for (Student currStudent : students) {
      Long currId = currStudent.getStudentId();

      if (currId > maxId) {
        maxId = currId;
      }
    }

    return maxId;
  }
}
