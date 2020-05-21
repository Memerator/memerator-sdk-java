package me.memerator.api.object;

import org.json.JSONObject;

import java.time.Instant;

public class User {
    JSONObject values;

    public User(JSONObject items) {
        values = items;
    }

    /**
     * @return (String) the user"s username
     */
    public String getUsername() {
        return values.getString("username");
    }

    /**
     * @return (Long) the user"s ID
     */
    public long getId() {
        return values.getLong("id");
    }

    /**
     * @return (String) the user"s bio
     */
    public String getBio() {
        try {
            return values.getString("bio");
        } catch (org.json.JSONException e) {
            return null;
        }
    }

    /**
     * @return (Integer) the amount of followers this user has
     */
    public int getFollowerCount() {
        return values.getJSONObject("stats").getInt("followers");
    }

    /**
     * @return (Integer) the amount of users this user is following
     */
    public int getFollowingCount() {
        return values.getJSONObject("stats").getInt("following");
    }

    /**
     * @return (Integer) the amount of memes this user has
     */
    public int getMemeCount() {
        return values.getJSONObject("stats").getInt("memes");
    }

    /**
     * @return (true, false) the user"s verification status
     */
    public boolean isVerified() {
        return values.getJSONObject("perks").getBoolean("verified");
    }

    /**
     * @return (true, false) the user"s staff member status
     */
    public boolean isStaff() {
        return values.getJSONObject("perks").getBoolean("staff");
    }

    /**
     * @return (true, false) the user"s translator status
     */
    public boolean isTranslator() {
        return values.getJSONObject("perks").getBoolean("translator");
    }

    /**
     * @return (true, false) the user"s pro status
     */
    public boolean isPro() {
        return values.getJSONObject("perks").getBoolean("pro");
    }

    /**
     * @return (String) the user"s profile link
     */
    public String getProfileUrl() {
        return values.getString("permalink");
    }

    /**
     * @return (String) the user's join time
     * @deprecated
     * @see User#getJoinTimestamp()
     */
    public String getJoinedAt() {
        return values.getString("joined");
    }


    /**
     * @return the user's join timestamp
     */
    public Instant getJoinTimestamp() {
        return Instant.ofEpochSecond(values.getLong("joined_epoch_seconds"));
    }
}
