package me.memerator.api.object;

import org.json.JSONObject;

import java.time.Instant;

public class Comment {
    JSONObject values;

    public Comment(JSONObject items) {
        values = items;
    }

    /**
     * @return this comment's ID.
     */
    public int getCommentId() {
        return values.getInt("id");
    }

    /**
     * @return the actual comment contents.
     */
    public String getCommentContent() {
        return values.getString("content");
    }

    /**
     * @return the timestamp of this comment
     */
    public Instant getTimestamp() {
        return Instant.ofEpochSecond(values.getLong("timestamp_epoch_seconds"));
    }

    /**
     * @return the author of this comment.
     */
    public User getAuthor() {
        return new User(values.getJSONObject("author"));
    }

    public Meme getAssociatedMeme() {
        return new Meme(values.getJSONObject("meme"));
    }
}
