package me.memerator.api.internal.impl.entities;

import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.entities.Comment;
import me.memerator.api.client.entities.Meme;
import me.memerator.api.client.entities.User;
import org.json.JSONObject;

import java.time.Instant;

public class CommentImpl implements Comment {
    JSONObject values;
    MemeratorAPI api;

    public CommentImpl(JSONObject items, MemeratorAPI api) {
        values = items;
        this.api = api;
    }

    @Override
    public int getCommentId() {
        return values.getInt("id");
    }

    @Override
    public String getCommentContent() {
        return values.getString("content");
    }

    @Override
    public Instant getTimestamp() {
        return Instant.ofEpochSecond(values.getLong("timestamp_epoch_seconds"));
    }

    @Override
    public User getAuthor() {
        return new UserImpl(values.getJSONObject("author"), api);
    }

    @Override
    public Meme getAssociatedMeme() {
        return new MemeImpl(values.getJSONObject("meme"), api);
    }
}
