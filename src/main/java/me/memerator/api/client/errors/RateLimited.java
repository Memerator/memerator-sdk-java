package me.memerator.api.client.errors;

public class RateLimited extends RuntimeException {
    public RateLimited(String message) {
        super(message);
    }
}
