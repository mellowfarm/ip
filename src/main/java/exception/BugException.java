package exception;

/**
 * Custom exception for Bug application-specific errors.
 * Provides meaningful error messages for various failure scenarios.
 */
public class BugException extends Exception {

    /**
     * Creates a new BugException with the specified error message.
     *
     * @param message the error message describing what went wrong
     */
    public BugException(String message) {
        super(message);
    } // Pass the message to the parent Exception class
}
