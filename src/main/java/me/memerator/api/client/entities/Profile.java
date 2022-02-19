package me.memerator.api.client.entities;

import me.memerator.api.client.MemeratorAPI;
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
    MemeratorAPI api;

    public Profile(JSONObject items, MemeratorAPI api) {
        super(items, api);
        values = items;
        this.api = api;
    }

    /**
     * @return the time when your Pro subscription expires, if you have one
     */
    @Nullable
    public String getProExpirationDate() {
        return values.getJSONObject("pro").getString("expires");
    }

    /**
     * Set your username<br>
     * Username requirements:<br>
     * 1) Be between 2 and 32 characters<br>
     * 2) Not taken<br>
     * 3) Can't be only numbers<br>
     * 4) No characters other than letters, numbers, periods, and underscores.<br>
     * If you are verified, you will lose verification.
     * @param username the username to change to
     * @throws IllegalArgumentException if the requirements weren't met
     */
    public void setUsername(String username) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("username", username);

        api.getAPI().post("profile/me", body);
    }

    /**
     * Set your bio
     * @param bio the new bio to set
     */
    public void setBio(String bio) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("bio", bio);

        api.getAPI().post("profile/me", body);
    }

    /**
     * @return your notifications
     */
    public List<Notification> getNotifications() {
        JSONArray notificationsraw = new JSONArray(api.getAPI().get("/notifications"));
        ArrayList<Notification> notifications = new ArrayList<>();
        for(int i = 0; i < notificationsraw.length(); i++) {
            notifications.add(new Notification((JSONObject) notificationsraw.get(i), api));
        }
        return notifications;
    }

    /**
     * @return your reports
     */
    public List<Report> getReports() {
        JSONArray reportResponse = new JSONArray(api.getAPI().get("/reports"));
        ArrayList<Report> reports = new ArrayList<>();
        for(int i = 0; i < reportResponse.length(); i++) {
            reports.add(new Report((JSONObject) reportResponse.get(i), api));
        }
        return reports;
    }

    /**
     * Gets a specific report by ID
     * @param id the report id
     * @return the report
     */
    public Report getReport(int id) {
        return new Report(new JSONObject(api.getAPI().get("/report/" + id)), api);
    }

    /**
     * @return your amount of notifications
     */
    public int getNotificationCount() {
        return new JSONObject(api.getAPI().get("/notifications/count")).getInt("count");
    }

    /**
     * Returns as a UserIntegrations object, from there you can get each integration type.
     * @return the integrations.
     */
    public List<UserIntegration> getIntegrations() {
        List<UserIntegration> integrations = new ArrayList<>();
        JSONObject integration = new JSONObject(api.getAPI().get("/integrations"));
        for(String service : integration.keySet()) {
            JSONArray datas = integration.getJSONArray(service);
            for(Object data : datas) {
                integrations.add(new UserIntegration(service, (String) data));
            }
        }
        return integrations;
    }
}
