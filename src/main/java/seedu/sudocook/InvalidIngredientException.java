package seedu.sudocook;

/**
 * Exception thrown when an ingredient fails validation checks.
 * Used for defensive programming to catch invalid ingredient data early.
 */
public class InvalidIngredientException extends Exception {
    /**
     * Constructs an InvalidIngredientException with the specified detail message.
     *
     * @param message The detail message describing what validation failed
     */
    public InvalidIngredientException(String message) {
        super(message);
    }

    /**
     * Constructs an InvalidIngredientException with the specified detail message and cause.
     *
     * @param message The detail message
     * @param cause The cause (another exception)
     */
    public InvalidIngredientException(String message, Throwable cause) {
        super(message, cause);
    }
}
