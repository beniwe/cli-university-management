package student.query;

import java.util.Optional;
import org.example.models.tables.pojos.Student;
import query.Query;
import student.storage.StudentRepository;

public class FindStudentQuery implements Query<Optional<Student>> {
  private final StudentRepository studentRepository;
  private final String searchId;

  public FindStudentQuery(StudentRepository studentRepository, String searchId) {
    this.studentRepository = studentRepository;
    this.searchId = searchId;
  }

  @Override
  public Optional<Student> execute() {
    return studentRepository.findById(searchId);
  }
}
