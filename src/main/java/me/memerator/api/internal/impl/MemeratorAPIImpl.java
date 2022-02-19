package me.memerator.api.internal.impl;

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
import me.memerator.api.internal.requests.RequestBuilder;
import me.memerator.api.internal.requests.Requester;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MemeratorAPIImpl implements MemeratorAPI {
    public String token;
    private final OkHttpClient client;

    public MemeratorAPIImpl(String apiKey) {
        this.token = apiKey;
        this.client = new OkHttpClient();
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String newToken) {
        token = newToken;
    }

    @Override
    public Requester<Meme> retrieveMeme(String id) {
        try {
            Request builder = RequestBuilder.get("meme/" + id, token).build();

            Function<String, Meme> meme = (String response) -> new MemeImpl(new JSONObject(response), this);

            return new Requester<>(client, builder, meme);
        } catch (NotFound notFound) {
            throw new NotFound("This meme doesn't exist!");
        }
    }

    @Override
    public Requester<User> retrieveUser(String username) {
        Request request = RequestBuilder.get("profile/" + username, token).build();
        Function<String, User> user = (String response) -> new UserImpl(new JSONObject(response), this);

        return new Requester<>(client, request, user);
    }

    @Override
    public Requester<Profile> retrieveProfile() {
        Request request = RequestBuilder.get("profile/me", token).build();
        Function<String, Profile> profile = (String response) -> new ProfileImpl(new JSONObject(response), this);

        return new Requester<>(client, request, profile);
    }

    @Override
    public Requester<Stats> retrieveStats() {
        Request request = RequestBuilder.get("stats", token).build();
        Function<String, Stats> stats = (String response) -> new StatsImpl(new JSONObject(response));

        return new Requester<>(client, request, stats);
    }

    @Override
    public Requester<Meme> retrieveRandomMeme() {
        return retrieveRandomMeme(Age.TEEN);
    }

    @Override
    public Requester<Meme> retrieveRandomMeme(Age max) {
        Request request = RequestBuilder.get("meme/random?age=" + max.getAgeInt(), token).build();
        Function<String, Meme> meme = (String response) -> new MemeImpl(new JSONObject(response), this);

        return new Requester<>(client, request, meme);
    }

    private final Function<String, List<Meme>> responseToMemeList = (String response) -> {
        List<Meme> memes = new ArrayList<>();
        JSONArray array = new JSONArray(response);
        for (int i = 0; i < array.length(); i++) {
            memes.add(new MemeImpl(array.getJSONObject(i), this));
        }
        return memes;
    };

    @Override
    public Requester<List<Meme>> searchMemes(String query) {
        Request request;

        try {
            request = RequestBuilder.get("meme/search?search=" + URLEncoder.encode(query, "UTF-8"), token).build();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("No UTF-8? What.");
        }

        Function<String, List<Meme>> memeList = (String response) -> {
            JSONArray memeresponse = new JSONArray(response);

            if(memeresponse.length() > 0) {
                ArrayList<Meme> memes = new ArrayList<>();
                for(int i = 0; i < memeresponse.length(); i++) {
                    memes.add(new MemeImpl((JSONObject) memeresponse.get(i), this));
                }
                return memes;
            } else {
                return new ArrayList<>();
            }
        };

        return new Requester<>(client, request, memeList);
    }

    @Override
    public Requester<List<Meme>> retrieveRecentMemes() {
        Request request = RequestBuilder.get("meme/recents", token).build();

        return new Requester<>(client, request, responseToMemeList);
    }

    @Override
    public Requester<List<Meme>> retrieveRecentMemes(int amount) {
        Request request = RequestBuilder.get("meme/recents?amount=" + amount, token).build();

        return new Requester<>(client, request, responseToMemeList);
    }

    @Override
    public Requester<List<Meme>> retrieveRecentMemes(int amount, int offset) {
        Request request = RequestBuilder.get("meme/recents?amount=" + amount + "&offset=" + offset, token).build();

        return new Requester<>(client, request, responseToMemeList);
    }

    @Override
    public Requester<String> submitMeme(JSONObject meme) {
        Request request = RequestBuilder.post("/submit", meme.toMap(), token).build();

        return new Requester<>(client, request, (String response) -> new JSONObject(response).getString("memeid"));
    }

    @Override
    public Requester<Map<String, List<TopMemer>>> retrieveTopMemers() {
        Request request = RequestBuilder.get("topmemers", token).build();

        Function<String, Map<String, List<TopMemer>>> topMemers = (String responseString) -> {
            JSONObject response = new JSONObject(responseString);

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
        };

        return new Requester<>(client, request, topMemers);
    }

    @Override
    public OkHttpClient getClient() {
        return client;
    }
}
