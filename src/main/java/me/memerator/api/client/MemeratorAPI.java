package me.memerator.api.client;

import me.memerator.api.client.entities.Age;
import me.memerator.api.client.entities.Meme;
import me.memerator.api.client.entities.Profile;
import me.memerator.api.client.entities.Stats;
import me.memerator.api.client.entities.TopMemer;
import me.memerator.api.client.entities.User;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface MemeratorAPI {
    /**
     * @return the token you entered
     */
    String getToken();

    /**
     * Changes the token
     * @param newToken the new token
     */
    void setToken(String newToken);

    /**
     * The API method used in the lib. Has base URL already set.
     * @return the API
     */
    API getAPI();

    /**
     * Gets a meme from the website by its Meme ID.
     * @param id the meme id
     * @return a Meme
     */
    Meme getMeme(String id);

    /**
     * Gets a user by username or user ID.
     * @param username the username or ID.
     * @return a User
     */
    User getUser(String username);

    /**
     * Gets the currently logged in ProfileImpl. Inherits User
     * @return your ProfileImpl
     */
    Profile getProfile();

    /**
     * The site's stats as seen on https://memerator.me/stats
     * @return a Stats object with the stats.
     */
    Stats getStats();

    /**
     * Gets a random meme from the website. Up to Teen by default.
     * @return a random meme
     */
    Meme getRandomMeme();

    /**
     * Gets a random meme from the website up a specified Max Age.
     * @param max the maximum allowed age group.
     * @return a Meme.
     */
    Meme getRandomMeme(Age max);

    /**
     * Searches the website for a meme. As seen on https://memerator.me/search
     * @param query the search query
     * @return an Array of Memes. Can be empty, never null.
     */
    List<Meme> searchMemes(String query);

    /**
     * Get the 25 most recent memes
     * @return a list of Memes
     */
    List<Meme> getRecentMemes();

    /**
     * Get the specified amount of recent memes, up to 25.
     * @param amount the amount of memes to get
     * @return the memes
     */
    List<Meme> getRecentMemes(int amount);

    /**
     * Get the specified amount (up to 25) of recent memes at a specific offset.
     * @param amount the amount of memes to get
     * @param offset the offset, anywhere from 0 to the total amount of memes.
     * @return the memes
     */
    List<Meme> getRecentMemes(int amount, int offset);

    /**
     * Submit a meme. See MemeBuilder class.
     * @param meme a prepared Meme
     * @return the submitted Meme.
     */
    Meme submitMeme(JSONObject meme);

    /**
     * Returns a list of hashmap with 3 items
     * Each item is time frame as key and a list of top memers as the value
     * 1d = Top for the past day
     * 7d = Top for the past week
     * 1mo = Top for the past month
     * @return the top memers
     */
    Map<String, List<TopMemer>> getTopMemers();
}
