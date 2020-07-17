package me.memerator.api.object;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserIntegrations {
    JSONObject values;

    public UserIntegrations(JSONObject items) {
        values = items;
    }

    /**
     * @return whether this user has any Discord integrations
     */
    public boolean hasDiscordIntegrations() {
        return values.has("discord");
    }

    /**
     * @return this user's Discord integrations, empty list if none
     */
    public List<Object> getDiscordIntegrations() {
        if(!hasDiscordIntegrations())
            return new ArrayList<>();
        return values.getJSONArray("discord").toList();
    }

    /**
     * @return whether this user has any Google integrations
     */
    public boolean hasGoogleIntegrations() {
        return values.has("google");
    }

    /**
     * @return this user's Google integrations, empty list if none
     */
    public List<Object> getGoogleIntegrations() {
        if(!hasGoogleIntegrations())
            return new ArrayList<>();
        return values.getJSONArray("google").toList();
    }

    /**
     * @return whether this user has any Apple integrations
     */
    public boolean hasAppleIntegrations() {
        return values.has("apple");
    }

    /**
     * @return this user's Discord integrations, empty list if none
     */
    public List<Object> getAppleIntegrations() {
        if(!hasAppleIntegrations())
            return new ArrayList<>();
        return values.getJSONArray("apple").toList();
    }

    /**
     * @return whether this user has any Discord integrations
     */
    public boolean hasMinecraftIntegrations() {
        return values.has("minecraft");
    }

    /**
     * @return this user's Discord integrations, empty list if none
     */
    public List<Object> getMinecraftIntegrations() {
        if(!hasMinecraftIntegrations())
            return new ArrayList<>();
        return values.getJSONArray("minecraft").toList();
    }
}
