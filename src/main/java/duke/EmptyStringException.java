package duke;

/**
 * Represents an exception thrown when an empty string is encountered.
 */
class EmptyStringException extends RuntimeException {
    /**
     * Constructs an EmptyStringException with the specified detail message.
     *
     * @param message The detail message.
     */
    public EmptyStringException(String message) {
        super(message);
    }
}