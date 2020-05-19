package me.memerator.api.object;

import me.memerator.api.MemeratorAPI;
import me.memerator.api.errors.*;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A profile is a user, but has more details.
 * Usually, only the authenticated user can see these.
 */
public class Profile extends User {
    JSONObject values;

    public Profile(JSONObject items) {
        super(items);
        values = items;
    }

    /**
     * @return the time when your Pro subscription expires, if you have one
     */
    public String getProExpirationDate() {
        return values.getJSONObject("pro").getString("expires");
    }

    /**
     * @return the time when your Pro subscription started, if you have one
     */
    public String getProStartDate() {
        return values.getJSONObject("pro").getString("since");
    }

    /**
     * Set your username
     * Username requirements:
     *   Be between 2 and 32 characters
     *   Not taken
     *   Can't be only numbers
     *   No characters other than letters, numbers, periods, and underscores.
     * @return true if it changed successfully
     * Throws IllegalArgumentException if the requirements weren't met
     */
    public boolean setUsername(String username) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("username", username);

        try {
            MemeratorAPI.api.post("profile/me", body);
            return true;
        } catch (RateLimited | InvalidToken | NotFound | Unauthorized | InternalServerError rateLimited) {
            rateLimited.printStackTrace();
        }
        return false;
    }

    /**
     * Set your bio
     * @return true if it changed successfully
     */
    public boolean setBio(String bio) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("bio", bio);

        try {
            MemeratorAPI.api.post("profile/me", body);
            return true;
        } catch (RateLimited | InvalidToken | NotFound | Unauthorized | InternalServerError rateLimited) {
            rateLimited.printStackTrace();
        }
        return false;
    }
}
