package me.memerator.api;

import me.memerator.api.errors.*;
import me.memerator.api.object.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

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

    public void setToken(String newToken) {
        token = newToken;
        api = new API(newToken);
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

    public Meme[] searchMemes(String query) throws Unauthorized, RateLimited, InvalidToken, NotFound, InternalServerError, UnsupportedEncodingException {
        JSONArray memeresponse = new JSONArray(getAPI().get("meme/search?search=" + URLEncoder.encode(query, "UTF-8")));
        if(memeresponse.length() > 0) {
            ArrayList<Meme> memes = new ArrayList<>();
            for(int i = 0; i < memeresponse.length(); i++) {
                memes.add(new Meme((JSONObject) memeresponse.get(i)));
            }
            Meme[] meme = new Meme[0];
            return memes.toArray(meme);
        } else {
            return new Meme[]{};
        }
    }
}
