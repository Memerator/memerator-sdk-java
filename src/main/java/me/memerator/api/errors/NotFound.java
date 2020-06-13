package me.memerator.api.errors;

public class NotFound extends RuntimeException {
    public NotFound(String message) {
        super(message);
    }
}
