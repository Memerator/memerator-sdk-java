package me.memerator.api;

import me.memerator.api.errors.*;
import me.memerator.api.object.Meme;
import me.memerator.api.object.User;

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

    public User getUser(String username) throws Unauthorized, RateLimited, InvalidToken, NotFound, InternalServerError {
        return new User(getAPI().get("profile/" + username, getToken()));
    }
}
