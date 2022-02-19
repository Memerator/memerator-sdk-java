package me.memerator.api.internal.impl.entities;

import me.memerator.api.client.entities.Stats;
import org.json.JSONObject;

/**
 * Website Statistics Class
 */
public class StatsImpl implements Stats {
    JSONObject values;

    public StatsImpl(JSONObject items) {
        values = items;
    }

    @Override
    public int getMemeCount() {
        return values.getInt("memes");
    }

    @Override
    public int getRatingsCount() {
        return values.getInt("ratings");
    }

    @Override
    public int getWebsiteUserCount() {
        return values.getInt("website_users");
    }

    @Override
    public int getUniqueMemerCount() {
        return values.getInt("unique_memers");
    }
}
