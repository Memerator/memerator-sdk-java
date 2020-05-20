package me.memerator.api.object;

import me.memerator.api.MemeratorAPI;
import me.memerator.api.errors.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;

public class Meme {
    JSONObject values;

    public Meme(JSONObject items) {
        values = items;
    }

    // @group Meme Information Methods

    /**
     * @return [String] the meme's ID.
     */
    public String getMemeId() {
        return values.getString("memeid");
    }

    /**
     * @return [String, null] the caption, if there is one.
     */
    public String getCaption() {
        return values.getString("caption");
    }

    /**
     * @return [String] the URL of the image.
     */
    public String getImageUrl() {
        return values.getString("url");
    }

    /**
     * @return [int] the total amount of ratings.
     */
    public int getTotalRatings() {
        return values.getJSONObject("rating").getInt("total");
    }

    /**
     * @return [Float] the average rating.
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
     * @return [User] the author of this meme
     */
    public User getAuthor() {
        return new User(values.getJSONObject("author"));
    }

    /**
     * @return [String] the URL to the meme
     */
    public String getMemeUrl() {
        return values.getString("permalink");
    }

    /**
     * Usually, the only people who can see disabled memes are Staff and the owners of the meme.
     * This will most likely always be false.
     * @return [true, false] the meme disabled status.
     */
    public boolean isDisabled() {
        return values.getBoolean("disabled");
    }

    /**
     * @return the comments for this meme
     */
    public Comment[] getComments() throws Unauthorized, RateLimited, InvalidToken, NotFound, InternalServerError {
        JSONArray commentsraw = new JSONArray(MemeratorAPI.api.get("/meme/" + getMemeId() + "/comments"));
        ArrayList<Comment> comments = new ArrayList<>();
        for(int i = 0; i < commentsraw.length(); i++) {
            comments.add(new Comment((JSONObject) commentsraw.get(i)));
        }
        Comment[] comm = new Comment[0];
        return comments.toArray(comm);
    }
}
