package me.memerator.api.errors;

public class InvalidToken extends Exception {
    public InvalidToken(String message) {
        super(message);
    }
}
