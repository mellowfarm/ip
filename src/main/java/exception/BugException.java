package exception;

/**
 * Custom exception class to handle errors specific to the Bug application.
 * This class extends the built-in Exception class to provide custom error messages.
 */
public class BugException extends Exception {

    /**
     * Constructs a new BugException with the specified detail message.
     *
     * @param message the detail message to be passed to the exception
     */
    public BugException(String message) {
        super(message);
    } // Pass the message to the parent Exception class
}
