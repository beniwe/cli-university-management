package student;

public class NoGradeException extends RuntimeException{

    public NoGradeException() {
        super();
    }

    public NoGradeException(String message) {
        super(message);
    }
}
