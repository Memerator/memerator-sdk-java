package me.memerator.api;

import me.memerator.api.errors.*;
import me.memerator.api.object.Meme;
import me.memerator.api.object.Profile;
import me.memerator.api.object.Stats;
import me.memerator.api.object.User;
import org.json.JSONObject;

public final class MemeratorAPI {
    public static String token;
    public static API api;

    public MemeratorAPI(String apiKey) {
        token = apiKey;
        api = new API(apiKey);
    }

    public String getToken() {
        return token;
    }

    public API getAPI() {
        return api;
    }

    public Meme getMeme(String id) throws Unauthorized, RateLimited, InvalidToken, InternalServerError, NotFound {
        try {
            return new Meme(new JSONObject(getAPI().get("meme/" + id)));
        } catch (NotFound notFound) {
            throw new NotFound("This meme doesn't exist!");
        }
    }

    public User getUser(String username) throws Unauthorized, RateLimited, InvalidToken, NotFound, InternalServerError {
        return new User(new JSONObject(getAPI().get("profile/" + username)));
    }

    public Profile getProfile() throws Unauthorized, RateLimited, InvalidToken, NotFound, InternalServerError {
        return new Profile(new JSONObject(getAPI().get("profile/me")));
    }

    public Stats getStats() throws Unauthorized, RateLimited, InvalidToken, NotFound, InternalServerError {
        return new Stats(new JSONObject(getAPI().get("stats")));
    }

    public Meme getRandomMeme() throws Unauthorized, RateLimited, InvalidToken, NotFound, InternalServerError {
        return new Meme(new JSONObject(getAPI().get("meme/random")));
    }
}
