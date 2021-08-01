package Exceptions;

public class StudentAlreadyEnrolledException extends Exception {

    public StudentAlreadyEnrolledException() {
        super();
    }

    public StudentAlreadyEnrolledException(String message) {
        super(message);
    }
}
