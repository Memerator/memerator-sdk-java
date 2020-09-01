package me.memerator.api.object;

import org.jetbrains.annotations.Nullable;
import me.memerator.api.MemeratorAPI;
import org.json.JSONObject;

import java.time.Instant;

public class Notification {
    JSONObject values;
    MemeratorAPI api;

    public Notification(JSONObject items, MemeratorAPI api) {
        values = items;
        this.api = api;
    }

    /**
     * @return the ID of this notification
     */
    public int getNotificationId() {
        return values.getInt("id");
    }

    /**
     * @return the sender of this notification.
     */
    public User getAuthor() {
        return new User(values.getJSONObject("sender"), api);
    }

    /**
     * @return The timestamp as an instant
     */
    public Instant getTimestamp() {
        return Instant.ofEpochSecond(values.getLong("timestamp_epoch_seconds"));
    }

    /**
     * @return the notification, formatted.
     */
    public String getMessageContent() {
        return values.getString("message");
    }

    /**
     * @return the raw message as it appears on Memerator.me
     */
    public String getRawMessageContent() {
        return values.getString("raw");
    }

    /**
     * The type is mostly used internally but is useful for clients implementing notification sorting.
     * Type 0 is a meme rating notification
     * Type 1 is a follow notification
     * Type 2 is a notice.
     * Type 3 is a report status update
     * @return the type of this message
     */
    public int getType() {
        return values.getInt("type");
    }

    /**
     * For meme ratings, the meme ID is returned, if you need it!
     *
     * @return the meme ID, if type == 0
     */
    @Nullable
    public String getAssociatedMemeID() {
        if (values.get("meme") == null) {
            return null;
        } else {
            return values.getJSONObject("meme").getString("memeid");
        }
    }

    /**
     * For meme ratings, the meme rating is returned, if you need it!
     * -1 if there is no meme associated.
     * @return the meme rating, if type == 0
     */
    public int getAssociatedMemeRating() {
        if (values.get("meme") == null) {
            return -1;
        } else {
            return values.getJSONObject("meme").getInt("rating");
        }
    }

    /**
     * Deletes this notification
     */
    public void delete() {
        api.getAPI().delete("/notification/" + getNotificationId());
    }
}
