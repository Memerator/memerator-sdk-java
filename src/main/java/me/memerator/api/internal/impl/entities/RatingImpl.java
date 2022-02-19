package me.memerator.api.internal.impl.entities;

import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.entities.Meme;
import me.memerator.api.client.entities.Rating;
import me.memerator.api.client.entities.User;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class RatingImpl implements Rating {
    JSONObject values;
    Meme meme;
    MemeratorAPI api;

    public RatingImpl(JSONObject items, Meme meme, MemeratorAPI api) {
        values = items;
        this.meme = meme;
        this.api = api;
    }

    @Override
    public User getUser() {
        return new UserImpl(values.getJSONObject("user"), api);
    }

    @Override
    public int getRating() {
        return values.getInt("rating");
    }

    @Override
    public OffsetDateTime getTimestamp() {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSX");
        return OffsetDateTime.parse(values.getString("timestamp"), inputFormat);
    }

    @Override
    public Meme getMeme() {
        return meme;
    }
}
