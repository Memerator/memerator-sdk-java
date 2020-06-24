package me.memerator.api;

import me.memerator.api.entity.MaxAge;
import me.memerator.api.errors.*;
import me.memerator.api.object.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class MemeratorAPI {
    public static String token;
    public static API api;

    public MemeratorAPI(String apiKey) {
        token = apiKey;
        api = new API(apiKey);
    }

    /**
     * @return the token you entered
     */
    public String getToken() {
        return token;
    }

    /**
     * Changes the token
     * @param newToken the new token
     */
    public void setToken(String newToken) {
        token = newToken;
        api = new API(newToken);
    }

    /**
     * The API method used in the lib. Has base URL already set.
     * @return the API
     */
    public API getAPI() {
        return api;
    }

    /**
     * Gets a meme from the website by its Meme ID.
     * @param id the meme id
     * @return a Meme
     */
    public Meme getMeme(String id) {
        try {
            return new Meme(new JSONObject(getAPI().get("meme/" + id)));
        } catch (NotFound notFound) {
            throw new NotFound("This meme doesn't exist!");
        }
    }

    /**
     * Gets a user by username or user ID.
     * @param username the username or ID.
     * @return a User
     */
    public User getUser(String username) {
        return new User(new JSONObject(getAPI().get("profile/" + username)));
    }

    /**
     * Gets the currently logged in Profile. Inherits User
     * @return your Profile
     */
    public Profile getProfile() {
        return new Profile(new JSONObject(getAPI().get("profile/me")));
    }

    /**
     * The site's stats as seen on https://memerator.me/stats
     * @return a Stats object with the stats.
     */
    public Stats getStats() {
        return new Stats(new JSONObject(getAPI().get("stats")));
    }

    /**
     * Gets a random meme from the website. Up to Teen by default.
     * @return a random meme
     */
    public Meme getRandomMeme() {
        return getRandomMeme(MaxAge.TEEN);
    }

    /**
     * Gets a random meme from the website up a specified Max Age.
     * @param max the maximum allowed age group.
     * @return a Meme.
     */
    public Meme getRandomMeme(MaxAge max) {
        return new Meme(new JSONObject(getAPI().get("meme/random?age=" + max.getAgeInt())));
    }

    /**
     * Searches the website for a meme. As seen on https://memerator.me/search
     * @param query the search query
     * @return an Array of Memes. Can be empty, never null.
     * @throws UnsupportedEncodingException this will never be thrown.
     */
    public List<Meme> searchMemes(String query) throws UnsupportedEncodingException {
        JSONArray memeresponse = new JSONArray(getAPI().get("meme/search?search=" + URLEncoder.encode(query, "UTF-8")));
        if(memeresponse.length() > 0) {
            ArrayList<Meme> memes = new ArrayList<>();
            for(int i = 0; i < memeresponse.length(); i++) {
                memes.add(new Meme((JSONObject) memeresponse.get(i)));
            }
            return memes;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Submit a meme. See MemeBuilder class.
     * @param meme a prepared Meme
     * @return the submitted Meme.
     */
    public Meme submitMeme(JSONObject meme) {
        if(meme.has("url")) {
            return getMeme(new JSONObject(getAPI().post("/submit", (HashMap<String, Object>) meme.toMap())).getString("memeid"));
        } else {
            return getMeme(new JSONObject(getAPI().post("/submit_image", (HashMap<String, Object>) meme.toMap())).getString("memeid"));
        }
    }
}
