package me.memerator.api.errors;

public class RateLimited extends RuntimeException {
    public RateLimited(String message) {
        super(message);
    }
}
