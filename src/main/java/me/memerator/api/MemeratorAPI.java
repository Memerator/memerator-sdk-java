package me.memerator.api;

import java.util.HashMap;

public final class MemeratorAPI {
    public static String token;

    public MemeratorAPI(String user_token) {
        token = user_token;
    }

    public String getToken() {
        return token;
    }

    public Meme getMeme(String id) {
        return new Meme(new HashMap<String, String>());
    }
}
