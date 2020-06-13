package me.memerator.api.object;

import me.memerator.api.MemeratorAPI;
import me.memerator.api.errors.*;
import org.json.JSONObject;

import java.time.Instant;

public class Notification {
    JSONObject values;

    public Notification(JSONObject items) {
        values = items;
    }

    /**
     * @return [Integer] the ID of this notification
     */
    public int getNotificationId() {
        return values.getInt("id");
    }

    /**
     * @return [User] the sender of this notification.
     */
    public User getAuthor() {
        return new User(values.getJSONObject("sender"));
    }

    /**
     * @return The timestamp as an instant
     */
    public Instant getTimestamp() {
        return Instant.ofEpochSecond(values.getLong("timestamp_epoch_seconds"));
    }

    /**
     * @return [String] the notification, formatted.
     */
    public String getMessageContent() {
        return values.getString("message");
    }

    /**
     * @return [String] the raw message as it appears on Memerator.me
     */
    public String getRawMessageContent() {
        return values.getString("'raw'");
    }


    /**
     * The type is mostly used internally but is useful for clients implementing notification sorting.
     * Type 0 is a meme rating notification
     * Type 1 is a follow notification
     * Type 2 is a notice.
     * Type 3 is a report status update
     *
     * @return [Integer] the type of this message
     */
    public int getType() {
        return values.getInt("type");
    }

    /**
     * For meme ratings, the meme ID is returned, if you need it!
     *
     * @return [String, nil] the meme ID, if type == 0
     */

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
     * @return [Integer, -1] the meme rating, if type == 0
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
        MemeratorAPI.api.delete("/notification/" + getNotificationId());
    }
}
