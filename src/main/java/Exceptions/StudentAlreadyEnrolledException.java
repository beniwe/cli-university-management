package Exceptions;

public class StudentAlreadyEnrolledException extends Error {

  public StudentAlreadyEnrolledException() {
    super();
  }

  public StudentAlreadyEnrolledException(String message) {
    super(message);
  }
}
