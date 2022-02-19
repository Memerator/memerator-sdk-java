package me.memerator.api.client.entities;

import me.memerator.api.internal.requests.Requester;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

public interface Notification {
    /**
     * @return the ID of this notification
     */
    int getNotificationId();

    /**
     * @return the sender of this notification.
     */
    User getAuthor();

    /**
     * @return The timestamp as an instant
     */
    Instant getTimestamp();

    /**
     * @return the notification, formatted.
     */
    String getMessageContent();

    /**
     * @return the raw message as it appears on Memerator.me
     */
    String getRawMessageContent();

    /**
     * The type is mostly used internally but is useful for clients implementing notification sorting.
     * Type 0 is a meme rating notification
     * Type 1 is a follow notification
     * Type 2 is a notice.
     * Type 3 is a report status update
     * @return the type of this message
     */
    int getType();

    /**
     * For meme ratings, the meme ID is returned, if you need it!
     *
     * @return the meme ID, if type == 0
     */
    @Nullable
    String getAssociatedMemeID();

    /**
     * For meme ratings, the meme rating is returned, if you need it!
     * -1 if there is no meme associated.
     * @return the meme rating, if type == 0
     */
    int getAssociatedMemeRating();

    /**
     * Deletes this notification
     */
    Requester<Void> delete();
}
