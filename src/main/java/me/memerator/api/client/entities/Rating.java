package me.memerator.api.client.entities;

import java.time.OffsetDateTime;

public interface Rating {
    /**
     * Get the user who rated this meme
     * @return the user
     */
    User getUser();

    /**
     * Get the rating the user gave
     * @return the rating
     */
    int getRating();

    /**
     * The timestamp of this rating as an OffsetDateTime
     * @return the timestamp
     */
    OffsetDateTime getTimestamp();

    /**
     * Get the meme associated with this Rating
     * @return the meme
     */
    Meme getMeme();
}
