package me.memerator.api.errors;

public class InternalServerError extends Exception {
    public InternalServerError(String message) {
        super(message);
    }
}
