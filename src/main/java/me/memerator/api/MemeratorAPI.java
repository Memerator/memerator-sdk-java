package me.memerator.api;

import me.memerator.api.object.Meme;

public final class MemeratorAPI {
    public static String token;
    public static API api;

    public MemeratorAPI(String apiKey) {
        token = apiKey;
        api = new API();
    }

    public String getToken() {
        return token;
    }

    public API getAPI() {
        return api;
    }

    public Meme getMeme(String id) {
        return new Meme(api.get("meme/" + id, getToken()));
    }
}
