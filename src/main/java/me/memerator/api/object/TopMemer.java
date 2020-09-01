package me.memerator.api.object;

import me.memerator.api.MemeratorAPI;
import org.json.JSONObject;

/**
 * A top memer. Has score, user, and rank
 */
public class TopMemer {
    JSONObject values;
    MemeratorAPI api;

    public TopMemer(JSONObject items, MemeratorAPI api) {
        values = items;
        this.api = api;
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
        return new User(values.getJSONObject("user"), api);
    }

    /**
     * @return their score this time frame
     */
    public int getScore() {
        return values.getInt("score");
    }
}
