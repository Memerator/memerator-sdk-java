package me.memerator.api.client.entities;

/**
 * Website Statistics Class
 */
public interface Stats {
    /**
     * @return the total number of (enabled) Memerator memes
     */
    int getMemeCount();

    /**
     * @return the total number of ratings
     */
    int getRatingsCount();

    /**
     * @return the number of registered website users
     */
    int getWebsiteUserCount();

    /**
     * @return the total number of people who have submitted a meme
     */
    int getUniqueMemerCount();
}
