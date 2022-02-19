package me.memerator.api.internal.impl.entities;

import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.entities.TopMemer;
import me.memerator.api.client.entities.User;
import org.json.JSONObject;

/**
 * A top memer. Has score, user, and rank
 */
public class TopMemerImpl implements TopMemer {
    JSONObject values;
    MemeratorAPI api;

    public TopMemerImpl(JSONObject items, MemeratorAPI api) {
        values = items;
        this.api = api;
    }

    @Override
    public int getRank() {
        return values.getInt("rank");
    }

    @Override
    public User getUser() {
        return new UserImpl(values.getJSONObject("user"), api);
    }

    @Override
    public int getScore() {
        return values.getInt("score");
    }
}
