package me.memerator.api.object;

import me.memerator.api.MemeratorAPI;
import me.memerator.api.errors.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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

    /**
     * @return your notifications
     * @throws Unauthorized if your api key cannot access the endpoint
     * @throws RateLimited if you hit the rate limit
     * @throws InvalidToken if your token is invalid
     * @throws NotFound if the notification endpoint somehow disappears
     * @throws InternalServerError if a server side error occurs
     */
    public Notification[] getNotifications() throws Unauthorized, RateLimited, InvalidToken, InternalServerError, NotFound {
        JSONArray notificationsraw = new JSONArray(MemeratorAPI.api.get("/notifications"));
        ArrayList<Notification> notifications = new ArrayList<>();
        for(int i = 0; i < notificationsraw.length(); i++) {
            notifications.add(new Notification((JSONObject) notificationsraw.get(i)));
        }
        Notification[] noti = new Notification[0];
        return notifications.toArray(noti);
    }

    /**
     * @return your amount of notifications
     */
    public int getNotificationCount() throws Unauthorized, RateLimited, InvalidToken, NotFound, InternalServerError {
        return new JSONObject(MemeratorAPI.api.get("/notifications/count")).getInt("count");
    }
}
