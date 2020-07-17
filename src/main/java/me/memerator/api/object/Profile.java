package me.memerator.api.object;

import me.memerator.api.MemeratorAPI;
import me.memerator.api.errors.*;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @Nullable
    public String getProExpirationDate() {
        return values.getJSONObject("pro").getString("expires");
    }

    /**
     * @return the time when your Pro subscription started, if you have one
     */
    @Nullable
    public String getProStartDate() {
        return values.getJSONObject("pro").getString("since");
    }

    /**
     * @return if you have an active pro subscription
     */
    public boolean isProActive() {
        return values.getJSONObject("pro").getBoolean("active");
    }

    /**
     * Set your username
     * Username requirements:
     *   Be between 2 and 32 characters
     *   Not taken
     *   Can't be only numbers
     *   No characters other than letters, numbers, periods, and underscores.
     * If you are verified, you will lose verification.
     * @param username the username to change to
     * Throws IllegalArgumentException if the requirements weren't met
     */
    public void setUsername(String username) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("username", username);

        MemeratorAPI.api.post("profile/me", body);
    }

    /**
     * Set your bio
     * @param bio the new bio to set
     */
    public void setBio(String bio) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("bio", bio);

        MemeratorAPI.api.post("profile/me", body);
    }

    /**
     * @return your notifications
     * @throws Unauthorized if your api key cannot access the endpoint
     * @throws RateLimited if you hit the rate limit
     * @throws InvalidToken if your token is invalid
     * @throws NotFound if the notification endpoint somehow disappears
     * @throws InternalServerError if a server side error occurs
     */
    public List<Notification> getNotifications() {
        JSONArray notificationsraw = new JSONArray(MemeratorAPI.api.get("/notifications"));
        ArrayList<Notification> notifications = new ArrayList<>();
        for(int i = 0; i < notificationsraw.length(); i++) {
            notifications.add(new Notification((JSONObject) notificationsraw.get(i)));
        }
        return notifications;
    }

    /**
     * @return your amount of notifications
     */
    public int getNotificationCount() {
        return new JSONObject(MemeratorAPI.api.get("/notifications/count")).getInt("count");
    }

    /**
     * @return a list of your memes
     */
    public List<Meme> getMemes() {
        JSONArray response = new JSONArray(MemeratorAPI.api.get("/mymemes"));
        List<Meme> memes = new ArrayList<>();
        for(int i = 0; i < response.length(); i++) {
            memes.add(new Meme((JSONObject) response.get(i)));
        }
        return memes;
    }

    /**
     * Returns as a UserIntegrations object, from there you can get each integration type.
     * @return the integrations.
     */
    public UserIntegrations getIntegrations() {
        return new UserIntegrations(new JSONObject(MemeratorAPI.api.get("/integrations")));
    }
}
