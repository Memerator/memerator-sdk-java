package me.memerator.api.object;

import me.memerator.api.MemeratorAPI;
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

    /**
     * @return the meme this comment was left on.
     */
    public Meme getAssociatedMeme() {
        return new Meme(values.getJSONObject("meme"));
    }

    /**
     * Deletes this comment, only works if you meet one of the following conditions
     * 1) You own the meme
     * 2) You made the comment
     */
    public void delete() {
        MemeratorAPI.api.delete("/notification/" + getCommentId());
    }
}
