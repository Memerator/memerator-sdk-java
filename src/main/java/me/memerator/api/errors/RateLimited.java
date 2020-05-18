package me.memerator.api.errors;

public class RateLimited extends Exception {
    public RateLimited(String message) {
        super(message);
    }
}
