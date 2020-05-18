package me.memerator.api.errors;

public class Unauthorized extends Exception {
    public Unauthorized(String message) {
        super(message);
    }
}
