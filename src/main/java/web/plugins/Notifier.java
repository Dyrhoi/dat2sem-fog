package web.plugins;

import validation.ValidationErrorException;

public class Notifier {

    private final Type type;
    private final String message;
    private ValidationErrorException errorException;

    public Notifier(Type type, String message) {
        this.type = type;
        this.message = message;
        this.errorException = null;
    }

    public Notifier(Type type, String message, ValidationErrorException errorException) {
        this.type = type;
        this.message = message;
        this.errorException = errorException;
    }

    public ValidationErrorException getErrorException() {
        return errorException;
    }

    public String getMessage() {
        return message;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        DANGER,
        WARNING,
        SUCCESS
    }
}
