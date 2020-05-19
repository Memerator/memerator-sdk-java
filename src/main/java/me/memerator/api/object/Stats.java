package me.memerator.api.object;

import org.json.JSONObject;

public class Stats {
    JSONObject values;

    public Stats(JSONObject items) {
        values = items;
    }

    /**
     * @return [Integer] the total number of (enabled) Memerator memes
     */
    public int getMemeCount() {
        return values.getInt("memes");
    }

    /**
     * @return [Integer] the total number of ratings
     */
    public int getRatingsCount() {
        return values.getInt("ratings");
    }

    /**
     * @return [Integer] the number of registered website users
     */
    public int getWebsiteUserCount() {
        return values.getInt("website_users");
    }

    /**
     * @return [Integer] the total number of people who have submitted a meme
     */
    public int getUniqueMemerCount() {
        return values.getInt("unique_memers");
    }
}
