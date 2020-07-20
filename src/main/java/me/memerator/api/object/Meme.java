package me.memerator.api.object;

import org.jetbrains.annotations.Nullable;
import me.memerator.api.MemeratorAPI;
import me.memerator.api.entity.Age;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Meme {
    JSONObject values;

    public Meme(JSONObject items) {
        values = items;
    }

    // @group Meme Information Methods

    /**
     * @return the meme's ID.
     */
    public String getMemeId() {
        return values.getString("memeid");
    }

    /**
     * @return the caption, if there is one.
     */
    @Nullable
    public String getCaption() {
        try {
            return values.getString("caption");
        } catch (org.json.JSONException e) {
            return null;
        }
    }

    /**
     * @return the URL of the image.
     */
    public String getImageUrl() {
        return values.getString("url");
    }

    /**
     * @return the total amount of ratings.
     */
    public int getTotalRatings() {
        return values.getJSONObject("rating").getInt("total");
    }

    /**
     * @return the average rating.
     */
    public Float getAverageRating() {
        return values.getJSONObject("rating").getFloat("average");
    }

    /**
     * @return The timestamp as an instant
     */
    public Instant getTimestamp() {
        return Instant.ofEpochSecond(values.getLong("timestamp_epoch_seconds"));
    }

    /**
     * @return the time string that appears on the website
     */
    public String getTimeAgoFormatted() {
        return values.getString("time_ago");
    }

    /**
     * @return the author of this meme
     */
    public User getAuthor() {
        return new User(values.getJSONObject("author"));
    }

    /**
     * @return the URL to the meme
     */
    public String getMemeUrl() {
        return values.getString("permalink");
    }

    /**
     * Usually, the only people who can see disabled memes are Staff and the owners of the meme.
     * This will most likely always be false.
     * @return the meme disabled status.
     */
    public boolean isDisabled() {
        return values.getBoolean("disabled");
    }

    /**
     * @return the comments for this meme
     */
    public List<Comment> getComments() {
        JSONArray commentsraw = new JSONArray(MemeratorAPI.api.get("/meme/" + getMemeId() + "/comments"));
        ArrayList<Comment> comments = new ArrayList<>();
        for(int i = 0; i < commentsraw.length(); i++) {
            comments.add(new Comment((JSONObject) commentsraw.get(i)));
        }
        return comments;
    }

    /**
     * Returns the Age as an Age enum
     * @return the Age
     */
    public Age getAgeRating() {
        return Age.fromInt(values.getInt("age"));
    }

    /**
     * Gets the ratings on this meme.
     * Requirements for a response:
     *   1) Be Pro
     *   2) Be the owner of the meme
     * @return a list of ratings
     */
    public List<Rating> getRatings() {
        JSONArray ratings = new JSONArray(MemeratorAPI.api.get("meme/" + getMemeId() + "/ratings"));
        List<Rating> response = new ArrayList<>();
        for(Object rating : ratings)
            response.add(new Rating((JSONObject) rating, this));
        return response;
    }

    /**
     * Disables this meme. Meme owner only.
     */
    public void disable() {
        MemeratorAPI.api.put("meme/" + getMemeId() + "/disable", new HashMap<>());
        values.put("disabled", true);
    }

    /**
     * Enable this meme. Meme owner only.
     */
    public void enable() {
        MemeratorAPI.api.put("meme/" + getMemeId() + "/enable", new HashMap<>());
        values.put("disabled", false);
    }

    /**
     * Set the caption
     * @param newcaption the caption to set
     */
    public void setCaption(String newcaption) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("caption", newcaption);
        MemeratorAPI.api.put("meme/" + getMemeId() + "/caption", body);
        values.put("caption", newcaption);
    }
}
