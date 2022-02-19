package me.memerator.api.client.entities;

/**
 * A top memer. Has score, user, and rank
 */
public interface TopMemer {
    /**
     * @return the ranking
     */
    int getRank();

    /**
     * @return the user in this spot
     */
    User getUser();

    /**
     * @return their score this time frame
     */
    int getScore();
}
