package me.memerator.api.internal.impl;

import me.memerator.api.client.API;
import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.entities.Age;
import me.memerator.api.client.entities.Meme;
import me.memerator.api.client.entities.Profile;
import me.memerator.api.client.entities.Stats;
import me.memerator.api.client.entities.TopMemer;
import me.memerator.api.client.entities.User;
import me.memerator.api.client.errors.NotFound;
import me.memerator.api.internal.impl.entities.MemeImpl;
import me.memerator.api.internal.impl.entities.ProfileImpl;
import me.memerator.api.internal.impl.entities.StatsImpl;
import me.memerator.api.internal.impl.entities.TopMemerImpl;
import me.memerator.api.internal.impl.entities.UserImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemeratorAPIImpl implements MemeratorAPI {
    public String token;
    public API api;

    public MemeratorAPIImpl(String apiKey) {
        this.token = apiKey;
        this.api = new API(apiKey);
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String newToken) {
        token = newToken;
        api = new API(newToken);
    }

    @Override
    public API getAPI() {
        return api;
    }

    @Override
    public Meme getMeme(String id) {
        try {
            return new MemeImpl(new JSONObject(getAPI().get("meme/" + id)), this);
        } catch (NotFound notFound) {
            throw new NotFound("This meme doesn't exist!");
        }
    }

    @Override
    public User getUser(String username) {
        return new UserImpl(new JSONObject(getAPI().get("profile/" + username)), this);
    }

    @Override
    public Profile getProfile() {
        return new ProfileImpl(new JSONObject(getAPI().get("profile/me")), this);
    }

    @Override
    public Stats getStats() {
        return new StatsImpl(new JSONObject(getAPI().get("stats")));
    }

    @Override
    public Meme getRandomMeme() {
        return getRandomMeme(Age.TEEN);
    }

    @Override
    public Meme getRandomMeme(Age max) {
        return new MemeImpl(new JSONObject(getAPI().get("meme/random?age=" + max.getAgeInt())), this);
    }

    @Override
    public List<Meme> searchMemes(String query) {
        JSONArray memeresponse;
        try {
            memeresponse = new JSONArray(getAPI().get("meme/search?search=" + URLEncoder.encode(query, "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("No UTF-8? What.");
        }
        if(memeresponse.length() > 0) {
            ArrayList<Meme> memes = new ArrayList<>();
            for(int i = 0; i < memeresponse.length(); i++) {
                memes.add(new MemeImpl((JSONObject) memeresponse.get(i), this));
            }
            return memes;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Meme> getRecentMemes() {
        JSONArray memeresponse = new JSONArray(getAPI().get("meme/recents"));
        ArrayList<Meme> memes = new ArrayList<>();
        for(int i = 0; i < memeresponse.length(); i++) {
            memes.add(new MemeImpl((JSONObject) memeresponse.get(i), this));
        }
        return memes;
    }

    @Override
    public List<Meme> getRecentMemes(int amount) {
        JSONArray memeresponse = new JSONArray(getAPI().get("meme/recents?amount=" + amount));
        ArrayList<Meme> memes = new ArrayList<>();
        for(int i = 0; i < memeresponse.length(); i++) {
            memes.add(new MemeImpl((JSONObject) memeresponse.get(i), this));
        }
        return memes;
    }

    @Override
    public List<Meme> getRecentMemes(int amount, int offset) {
        JSONArray memeresponse = new JSONArray(getAPI().get("meme/recents?amount=" + amount + "&offset=" + offset));
        ArrayList<Meme> memes = new ArrayList<>();
        for(int i = 0; i < memeresponse.length(); i++) {
            memes.add(new MemeImpl((JSONObject) memeresponse.get(i), this));
        }
        return memes;
    }

    @Override
    public Meme submitMeme(JSONObject meme) {
        return getMeme(new JSONObject(getAPI().post("/submit", (HashMap<String, Object>) meme.toMap())).getString("memeid"));
    }

    @Override
    public Map<String, List<TopMemer>> getTopMemers() {
        JSONObject response = new JSONObject(getAPI().get("topmemers"));
        JSONArray oneDay = response.getJSONArray("1d");
        List<TopMemer> oneDayList = new ArrayList<>();
        for(Object user : oneDay) {
            oneDayList.add(new TopMemerImpl((JSONObject) user, this));
        }
        JSONArray oneWeek = response.getJSONArray("7d");
        List<TopMemer> oneWeekList = new ArrayList<>();
        for(Object user : oneWeek) {
            oneWeekList.add(new TopMemerImpl((JSONObject) user, this));
        }
        JSONArray oneMonth = response.getJSONArray("1mo");
        List<TopMemer> oneMonthList = new ArrayList<>();
        for(Object user : oneMonth) {
            oneMonthList.add(new TopMemerImpl((JSONObject) user, this));
        }
        Map<String, List<TopMemer>> tops = new HashMap<>();
        tops.put("1d", oneDayList);
        tops.put("7d", oneWeekList);
        tops.put("1mo", oneMonthList);
        return tops;
    }
}
