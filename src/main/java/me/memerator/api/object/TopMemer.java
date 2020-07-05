package me.memerator.api.object;

import org.json.JSONObject;

/**
 * A top memer. Has score, user, and rank
 */
public class TopMemer {
    JSONObject values;

    public TopMemer(JSONObject items) {
        values = items;
    }

    /**
     * @return the ranking
     */
    public int getRank() {
        return values.getInt("rank");
    }

    /**
     * @return the user in this spot
     */
    public User getUser() {
        return new User(values.getJSONObject("user"));
    }

    /**
     * @return their score this time frame
     */
    public int getScore() {
        return values.getInt("score");
    }
}
