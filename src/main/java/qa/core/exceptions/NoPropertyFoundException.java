package qa.core.exceptions;

public class NoPropertyFoundException extends RuntimeException {
    public NoPropertyFoundException(String key) {
        super("No property found for key : "+key);
    }
}

