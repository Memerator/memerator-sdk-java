package me.memerator.api.internal.impl.entities;

import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.entities.Age;
import me.memerator.api.client.entities.Comment;
import me.memerator.api.client.entities.Meme;
import me.memerator.api.client.entities.Rating;
import me.memerator.api.client.entities.User;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemeImpl implements Meme {
    JSONObject values;
    MemeratorAPI api;

    public MemeImpl(JSONObject items, MemeratorAPI api) {
        values = items;
        this.api = api;
    }

    @Override
    public String getMemeId() {
        return values.getString("memeid");
    }

    @Override
    @Nullable
    public String getCaption() {
        if(values.isNull("caption"))
            return null;
        return values.getString("caption");
    }

    @Override
    public String getImageUrl() {
        return values.getString("url");
    }

    @Override
    public int getTotalRatings() {
        return values.getJSONObject("rating").getInt("total");
    }

    @Override
    public Float getAverageRating() {
        return values.getJSONObject("rating").getFloat("average");
    }

    @Override
    public Instant getTimestamp() {
        return Instant.ofEpochSecond(values.getLong("timestamp_epoch_seconds"));
    }

    @Override
    public String getTimeAgoFormatted() {
        return values.getString("time_ago");
    }

    @Override
    public User getAuthor() {
        return new UserImpl(values.getJSONObject("author"), api);
    }

    @Override
    public String getMemeUrl() {
        return values.getString("permalink");
    }

    @Override
    public boolean isDisabled() {
        return values.getBoolean("disabled");
    }

    @Override
    public List<Comment> getComments() {
        JSONArray commentsraw = new JSONArray(api.getAPI().get("/meme/" + getMemeId() + "/comments"));
        ArrayList<Comment> comments = new ArrayList<>();
        for(int i = 0; i < commentsraw.length(); i++) {
            comments.add(new CommentImpl((JSONObject) commentsraw.get(i), api));
        }
        return comments;
    }

    @Override
    public Age getAgeRating() {
        return Age.fromInt(values.getInt("age"));
    }

    @Override
    public List<Rating> getRatings() {
        JSONArray ratings = new JSONArray(api.getAPI().get("meme/" + getMemeId() + "/ratings"));
        List<Rating> response = new ArrayList<>();
        for(Object rating : ratings)
            response.add(new RatingImpl((JSONObject) rating, this, api));
        return response;
    }

    @Override
    public Rating getOwnRating() {
        return new RatingImpl(new JSONObject(api.getAPI().get("meme/" + getMemeId() + "/rating")), this, api);
    }

    @Override
    public void disable() {
        api.getAPI().put("meme/" + getMemeId() + "/disable", new HashMap<>());
        values.put("disabled", true);
    }

    @Override
    public void enable() {
        api.getAPI().put("meme/" + getMemeId() + "/enable", new HashMap<>());
        values.put("disabled", false);
    }

    @Override
    public void setCaption(String newcaption) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("caption", newcaption);
        api.getAPI().put("meme/" + getMemeId() + "/caption", body);
        values.put("caption", newcaption);
    }

    @Override
    public void rate(int rating) {
        if(!(rating >= 1 && rating <= 5)) {
            throw new IllegalArgumentException("Enter a number between 1 and 5!");
        }
        HashMap<String, Object> body = new HashMap<>();
        body.put("rating", rating);
        api.getAPI().post("meme/" + getMemeId() + "/rate", body);
    }
}
