package me.memerator.api;

import me.memerator.api.errors.*;
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

    public Meme getMeme(String id) throws Unauthorized, RateLimited, InvalidToken, InternalServerError, NotFound {
        try {
            return new Meme(getAPI().get("meme/" + id, getToken()));
        } catch (NotFound notFound) {
            throw new NotFound("This meme doesn't exist!");
        }

    }
}
