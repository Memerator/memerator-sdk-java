package me.memerator.api.object;

import org.json.JSONObject;

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
     * @return [String] the time this meme was submitted.
     */
    public String getTimestamp() {
        return values.getString("timestamp");
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
}
