package me.memerator.api.internal.impl.entities;

import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.entities.Notification;
import me.memerator.api.client.entities.User;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.time.Instant;

public class NotificationImpl implements Notification {
    JSONObject values;
    MemeratorAPI api;

    public NotificationImpl(JSONObject items, MemeratorAPI api) {
        values = items;
        this.api = api;
    }

    @Override
    public int getNotificationId() {
        return values.getInt("id");
    }

    @Override
    public User getAuthor() {
        return new UserImpl(values.getJSONObject("sender"), api);
    }

    @Override
    public Instant getTimestamp() {
        return Instant.ofEpochSecond(values.getLong("timestamp_epoch_seconds"));
    }

    @Override
    public String getMessageContent() {
        return values.getString("message");
    }

    @Override
    public String getRawMessageContent() {
        return values.getString("raw");
    }

    @Override
    public int getType() {
        return values.getInt("type");
    }

    @Override
    @Nullable
    public String getAssociatedMemeID() {
        if (values.get("meme") == null) {
            return null;
        } else {
            return values.getJSONObject("meme").getString("memeid");
        }
    }

    @Override
    public int getAssociatedMemeRating() {
        if (values.get("meme") == null) {
            return -1;
        } else {
            return values.getJSONObject("meme").getInt("rating");
        }
    }

    @Override
    public void delete() {
        api.getAPI().delete("/notification/" + getNotificationId());
    }
}
