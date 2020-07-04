package me.memerator.api.object;

import me.memerator.api.MemeratorAPI;
import me.memerator.api.entity.MaxAge;
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
     * @return [String] the meme's ID.
     */
    public String getMemeId() {
        return values.getString("memeid");
    }

    /**
     * @return [String, null] the caption, if there is one.
     */
    public String getCaption() {
        try {
            return values.getString("caption");
        } catch (org.json.JSONException e) {
            return null;
        }
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
    public List<Comment> getComments() {
        JSONArray commentsraw = new JSONArray(MemeratorAPI.api.get("/meme/" + getMemeId() + "/comments"));
        ArrayList<Comment> comments = new ArrayList<>();
        for(int i = 0; i < commentsraw.length(); i++) {
            comments.add(new Comment((JSONObject) commentsraw.get(i)));
        }
        return comments;
    }

    /**
     * 1 => Family Friendly
     * 2 => Teen
     * 4 => Mature
     * @return the age rating
     */
    public int getAgeRating() {
        return values.getInt("age");
    }

    /**
     * Returns the Age as a MaxAge enum
     * @return the MaxAge
     */
    public MaxAge getAgeAsMaxAge() {
        return MaxAge.fromAge(getAgeRating());
    }

    /**
     * @return if the meme is family friendly (age == 1)
     */
    public boolean isFamilyFriendly() {
        return getAgeRating() == 1;
    }

    /**
     * @return if the meme is for teens (age == 2)
     */
    public boolean isTeen() {
        return getAgeRating() == 2;
    }

    /**
     * @return if the meme is mature (age == 4)
     */
    public boolean isMature() {
        return getAgeRating() == 4;
    }

    /**
     * @return the age string as it appears on the website
     */
    public String getAgeString() {
        if(isFamilyFriendly()) {
            return "Family Friendly";
        } else if(isTeen()) {
            return "Teen";
        } else {
            return "Mature";
        }
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
