package me.memerator.api;

public final class MemeratorAPI {
    public static String token;

    public MemeratorAPI(String user_token) {
        token = user_token;
    }

    public String getToken() {
        return token;
    }
}
