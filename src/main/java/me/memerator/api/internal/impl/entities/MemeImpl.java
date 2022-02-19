package me.memerator.api.internal.impl.entities;

import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.entities.Age;
import me.memerator.api.client.entities.Comment;
import me.memerator.api.client.entities.Meme;
import me.memerator.api.client.entities.Rating;
import me.memerator.api.client.entities.User;
import me.memerator.api.internal.requests.RequestBuilder;
import me.memerator.api.internal.requests.Requester;
import okhttp3.Request;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

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
    public Requester<List<Comment>> retrieveComments() {
        Request request = RequestBuilder.get("meme/" + getMemeId() + "/comments", api.getToken()).build();

        Function<String, List<Comment>> function = (String object) -> {
            JSONArray commentsraw = new JSONArray(object);

            ArrayList<Comment> comments = new ArrayList<>();
            for(int i = 0; i < commentsraw.length(); i++) {
                comments.add(new CommentImpl((JSONObject) commentsraw.get(i), api));
            }
            return comments;
        };

        return new Requester<>(api.getClient(), request, function);
    }

    @Override
    public Age getAgeRating() {
        return Age.fromInt(values.getInt("age"));
    }

    @Override
    public Requester<List<Rating>> retrieveRatings() {
        Request request = RequestBuilder.get("meme/" + getMemeId() + "/ratings", api.getToken()).build();

        Function<String, List<Rating>> function = (String object) -> {
            JSONArray ratings = new JSONArray(object);
            List<Rating> response = new ArrayList<>();
            for(Object rating : ratings)
                response.add(new RatingImpl((JSONObject) rating, this, api));
            return response;
        };

        return new Requester<>(api.getClient(), request, function);
    }

    @Override
    public Requester<Rating> retrieveOwnRating() {
        Request request = RequestBuilder.get("meme/" + getMemeId() + "/rating", api.getToken()).build();
        Function<String, Rating> function = (String rating) -> new RatingImpl(new JSONObject(rating), this, api);

        return new Requester<>(api.getClient(), request, function);
    }

    @Override
    public Requester<Void> disable() {
        Request request = RequestBuilder.put("meme/" + getMemeId() + "/disable", new HashMap<>(), api.getToken()).build();
        values.put("disabled", true);

        return new Requester<>(api.getClient(), request, (String object) -> null);
    }

    @Override
    public Requester<Void> enable() {
        Request request = RequestBuilder.put("meme/" + getMemeId() + "/enable", new HashMap<>(), api.getToken()).build();
        values.put("disabled", false);

        return new Requester<>(api.getClient(), request, (String object) -> null);
    }

    @Override
    public Requester<Void> setCaption(String newcaption) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("caption", newcaption);

        Request request = RequestBuilder.put("meme/" + getMemeId() + "/caption", body, api.getToken()).build();
        values.put("caption", newcaption);

        return new Requester<>(api.getClient(), request, (String object) -> null);
    }

    @Override
    public Requester<Void> rate(int rating) {
        if(!(rating >= 1 && rating <= 5)) {
            throw new IllegalArgumentException("Enter a number between 1 and 5!");
        }
        HashMap<String, Object> body = new HashMap<>();
        body.put("rating", rating);

        Request request = RequestBuilder.put("meme/" + getMemeId() + "/rate", body, api.getToken()).build();
        return new Requester<>(api.getClient(), request, (String object) -> null);
    }
}
