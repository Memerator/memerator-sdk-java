package me.memerator.api.object;

import org.json.JSONObject;

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
     * @return [String] the time this notification was sent.
     */
    public String getTimestamp() {
        return values.getString("timestamp");
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
}
