package me.memerator.api;

import me.memerator.api.entity.Age;
import me.memerator.api.errors.*;
import me.memerator.api.object.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return getRandomMeme(Age.TEEN);
    }

    /**
     * Gets a random meme from the website up a specified Max Age.
     * @param max the maximum allowed age group.
     * @return a Meme.
     */
    public Meme getRandomMeme(Age max) {
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
     * Get the 25 most recent memes
     * @return a list of Memes
     */
    public List<Meme> getRecentMemes() {
        JSONArray memeresponse = new JSONArray(getAPI().get("meme/recents"));
        ArrayList<Meme> memes = new ArrayList<>();
        for(int i = 0; i < memeresponse.length(); i++) {
            memes.add(new Meme((JSONObject) memeresponse.get(i)));
        }
        return memes;
    }

    /**
     * Get the specified amount of recent memes, up to 25.
     * @param amount the amount of memes to get
     * @return the memes
     */
    public List<Meme> getRecentMemes(int amount) {
        JSONArray memeresponse = new JSONArray(getAPI().get("meme/recents?amount=" + amount));
        ArrayList<Meme> memes = new ArrayList<>();
        for(int i = 0; i < memeresponse.length(); i++) {
            memes.add(new Meme((JSONObject) memeresponse.get(i)));
        }
        return memes;
    }

    /**
     * Get the specified amount (up to 25) of recent memes at a specific offset.
     * @param amount the amount of memes to get
     * @param offset the offset, anywhere from 0 to the total amount of memes.
     * @return the memes
     */
    public List<Meme> getRecentMemes(int amount, int offset) {
        JSONArray memeresponse = new JSONArray(getAPI().get("meme/recents?amount=" + amount + "&offset=" + offset));
        ArrayList<Meme> memes = new ArrayList<>();
        for(int i = 0; i < memeresponse.length(); i++) {
            memes.add(new Meme((JSONObject) memeresponse.get(i)));
        }
        return memes;
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

    /**
     * Returns a list of hashmap with 3 items
     * Each item is time frame as key and a list of top memers as the value
     * 1d = Top for the past day
     * 7d = Top for the past week
     * 1mo = Top for the past month
     * @return the top memers
     */
    public Map<String, List<TopMemer>> getTopMemers() {
        JSONObject response = new JSONObject(getAPI().get("topmemers"));
        JSONArray oneDay = response.getJSONArray("1d");
        List<TopMemer> oneDayList = new ArrayList<>();
        for(Object user : oneDay) {
            oneDayList.add(new TopMemer((JSONObject) user));
        }
        JSONArray oneWeek = response.getJSONArray("7d");
        List<TopMemer> oneWeekList = new ArrayList<>();
        for(Object user : oneWeek) {
            oneWeekList.add(new TopMemer((JSONObject) user));
        }
        JSONArray oneMonth = response.getJSONArray("1mo");
        List<TopMemer> oneMonthList = new ArrayList<>();
        for(Object user : oneMonth) {
            oneMonthList.add(new TopMemer((JSONObject) user));
        }
        Map<String, List<TopMemer>> tops = new HashMap<>();
        tops.put("1d", oneDayList);
        tops.put("7d", oneWeekList);
        tops.put("1mo", oneMonthList);
        return tops;
    }
}
