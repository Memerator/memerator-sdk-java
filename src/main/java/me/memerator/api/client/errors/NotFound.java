package me.memerator.api.client.errors;

public class NotFound extends RuntimeException {
    public NotFound(String message) {
        super(message);
    }
}
