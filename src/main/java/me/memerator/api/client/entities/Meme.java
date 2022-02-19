package me.memerator.api.client.entities;

import me.memerator.api.internal.requests.Requester;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.List;

public interface Meme {
    /**
     * @return the meme's ID.
     */
    String getMemeId();

    /**
     * @return the caption, if there is one.
     */
    @Nullable
    String getCaption();

    /**
     * @return the URL of the image.
     */
    String getImageUrl();

    /**
     * @return the total amount of ratings.
     */
    int getTotalRatings();

    /**
     * @return the average rating.
     */
    Float getAverageRating();

    /**
     * @return The timestamp as an instant
     */
    Instant getTimestamp();

    /**
     * @return the time string that appears on the website
     */
    String getTimeAgoFormatted();

    /**
     * @return the author of this meme
     */
    User getAuthor();

    /**
     * @return the URL to the meme
     */
    String getMemeUrl();

    /**
     * Usually, the only people who can see disabled memes are Staff and the owners of the meme.
     * This will most likely always be false.
     * @return the meme disabled status.
     */
    boolean isDisabled();

    /**
     * @return the comments for this meme
     */
    Requester<List<Comment>> retrieveComments();

    /**
     * Returns the Age as an Age enum
     * @return the Age
     */
    Age getAgeRating();

    /**
     * Gets the ratings on this meme.<br>
     * Requirements for a response:<br>
     * 1) Be Pro<br>
     * 2) Be the owner of the meme
     * @return a list of ratings
     */
    Requester<List<Rating>> retrieveRatings();

    /**
     * Gets your rating on the meme
     * @return your rating
     */
    Requester<Rating> retrieveOwnRating();

    /**
     * Disables this meme. Meme owner only.
     * @return a requester to disable
     */
    Requester<Void> disable();

    /**
     * Enable this meme. Meme owner only.
     * @return a requester to enable
     */
    Requester<Void> enable();

    /**
     * Set the caption
     * @param newcaption the caption to set
     * @return a requester for the caption
     */
    Requester<Void> setCaption(String newcaption);

    /**
     * Rate this meme, requires "Ratings" key permission
     * May cause inaccuracies for Meme#getAverageRating() and Meme#getTotalRating()
     * @param rating the rating, between 1 and 5
     * @throws IllegalArgumentException if you put an invalid rating
     * @return a requester for the rating
     */
    Requester<Void> rate(int rating);
}
