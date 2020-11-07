package me.memerator.api.object;

import me.memerator.api.MemeratorAPI;
import me.memerator.api.entity.UserPerk;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Color;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class User {
    JSONObject values;
    MemeratorAPI api;

    public User(JSONObject items, MemeratorAPI api) {
        values = items;
        this.api = api;
    }

    /**
     * @return the user's username
     */
    public String getUsername() {
        return values.getString("username");
    }

    /**
     * @return the user's ID
     */
    public long getId() {
        return values.getLong("id");
    }

    /**
     * @return the user's bio or null if there isn't one
     */
    public String getBio() {
        try {
            return values.getString("bio");
        } catch (org.json.JSONException e) {
            return null;
        }
    }

    /**
     * @return the amount of followers this user has
     */
    public int getFollowerCount() {
        return values.getJSONObject("stats").getInt("followers");
    }

    /**
     * @return the amount of users this user is following
     */
    public int getFollowingCount() {
        return values.getJSONObject("stats").getInt("following");
    }

    /**
     * @return the amount of memes this user has
     */
    public int getMemeCount() {
        return values.getJSONObject("stats").getInt("memes");
    }

    /**
     * @return the user's verification status
     * @deprecated
     * @see User#getUserPerks()
     */
    public boolean isVerified() {
        return values.getJSONObject("perks").getBoolean("verified");
    }

    /**
     * @return the user's staff member status
     * @deprecated
     * @see User#getUserPerks()
     */
    public boolean isStaff() {
        return values.getJSONObject("perks").getBoolean("staff");
    }

    /**
     * @return the user's translator status
     * @deprecated
     * @see User#getUserPerks()
     */
    public boolean isTranslator() {
        return values.getJSONObject("perks").getBoolean("translator");
    }

    /**
     * @return the user's pro status
     * @deprecated
     * @see User#getUserPerks()
     */
    public boolean isPro() {
        return values.getJSONObject("perks").getBoolean("pro");
    }

    /**
     * A service account is an account specifically for doing things for Memerator, and is not human owned.
     * They don't have any memes
     * @return the user's service status
     * @deprecated
     * @see User#getUserPerks()
     */
    public boolean isService() {
        return values.getJSONObject("perks").getBoolean("service");
    }

    /**
     * @return the user's founder status
     * @deprecated
     * @see User#getUserPerks()
     */
    public boolean isFounder() {
        return values.getJSONObject("perks").getBoolean("founder");
    }

    /**
     * @return a list of this user's public perks
     */
    public List<UserPerk> getUserPerks() {
        List<UserPerk> perks = new ArrayList<>();
        for (String object : values.getJSONObject("perks").keySet()) {
            boolean has = values.getJSONObject("perks").getBoolean(object);
            if (has) {
                perks.add(UserPerk.valueOf(object.toUpperCase()));
            }
        }
        return perks;
    }

    /**
     * Shorthand for getUserPerks().contains(perk);
     * @param perk the perk to check
     * @return true/false if they have the specified perk
     */
    public boolean hasPerk(UserPerk perk) {
        return getUserPerks().contains(perk);
    }

    /**
     * @return the user's profile link
     */
    public String getProfileUrl() {
        return values.getString("permalink");
    }

    /**
     * @return the user's join timestamp
     */
    public Instant getJoinTimestamp() {
        return Instant.ofEpochSecond(values.getLong("joined_epoch_seconds"));
    }

    /**
     * @return a list of this user's memes
     */
    public List<Meme> getMemes() {
        // Services don't have memes
        if(isService())
            return new ArrayList<>();
        JSONArray response = new JSONArray(api.getAPI().get("/profile/" + getId() + "/memes"));
        List<Meme> memes = new ArrayList<>();
        for(int i = 0; i < response.length(); i++) {
            memes.add(new Meme((JSONObject) response.get(i), api));
        }
        return memes;
    }


    /**
     * @return the time when your Pro subscription started, if you have one
     * @see User#getProSince
     */
    @Nullable
    @Deprecated
    public String getProStartDate() {
        if (hasPerk(UserPerk.PRO)) {
            return values.getJSONObject("pro").getString("since");
        } else {
            return null;
        }
    }

    /**
     * The timestamp of the Pro start as an OffsetDateTime
     * @return the timestamp
     */
    @Nullable
    public OffsetDateTime getProSince() {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSX");
        if (hasPerk(UserPerk.PRO)) {
            return OffsetDateTime.parse(values.getString("timestamp"), inputFormat);
        } else {
            return null;
        }
    }

    /**
     * This method actually differs from User#isPro() as they are 2 separate checks.
     * They should always match, rarely is this value used outside of the Billing page.
     * @return if you have an active pro subscription
     */
    public boolean isProActive() {
        if (hasPerk(UserPerk.PRO)) {
            return values.getJSONObject("pro").getBoolean("active");
        } else {
            return false;
        }
    }

    /**
     * Get the Pro name color for this user. If they're not pro, or don't have one set, this will be null.
     * @return the Pro name color
     */
    @Nullable
    public Color getNameColor() {
        if (hasPerk(UserPerk.PRO)) {
            String color = values.getJSONObject("pro").getString("name_color");
            if (color == null) {
                return null;
            }
            return Color.decode("#" + color);
        } else {
            return null;
        }
    }

    /**
     * Get the Pro background color for this user. If they're not Pro, or don't have one set, this will be null.
     * @return the Pro background color
     */
    @Nullable
    public Color getBackgroundColor() {
        if (hasPerk(UserPerk.PRO)) {
            String color = values.getJSONObject("pro").getString("background_color");
            if (color == null) {
                return null;
            }
            return Color.decode("#" + color);
        } else {
            return null;
        }
    }

    /**
     * The Pro background URL for this user. If they're not Pro, or don't have one set, this will be null.
     * @return the Pro Background URL.
     */
    @Nullable
    public String getBackgroundUrl() {
        if (hasPerk(UserPerk.PRO)) {
            return values.getJSONObject("pro").getString("background_url");
        } else {
            return null;
        }
    }
}
