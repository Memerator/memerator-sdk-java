package me.memerator.api.object;

import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Rating {
    JSONObject values;
    Meme meme;

    public Rating(JSONObject items, Meme meme) {
        values = items;
        this.meme = meme;
    }

    /**
     * Get the user who rated this meme
     * @return the user
     */
    public User getUser() {
        return new User(values.getJSONObject("user"));
    }

    /**
     * Get the rating the user gave
     * @return the rating
     */
    public int getRating() {
        return values.getInt("rating");
    }

    /**
     * The timestamp of this rating as an OffsetDateTime
     * @return the timestamp
     */
    public OffsetDateTime getTimestamp() {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSX");
        return OffsetDateTime.parse(values.getString("timestamp"), inputFormat);
    }

    /**
     * Get the meme associated with this Rating
     * @return the meme
     */
    public Meme getMeme() {
        return meme;
    }
}
