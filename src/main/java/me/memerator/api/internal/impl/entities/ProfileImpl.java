package me.memerator.api.internal.impl.entities;

import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.entities.Notification;
import me.memerator.api.client.entities.Profile;
import me.memerator.api.client.entities.Report;
import me.memerator.api.client.entities.UserIntegration;
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
public class ProfileImpl extends UserImpl implements Profile {
    JSONObject values;
    MemeratorAPI api;

    public ProfileImpl(JSONObject items, MemeratorAPI api) {
        super(items, api);
        values = items;
        this.api = api;
    }

    @Override
    @Nullable
    public String getProExpirationDate() {
        return values.getJSONObject("pro").getString("expires");
    }

    @Override
    public void setUsername(String username) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("username", username);

        api.getAPI().post("profile/me", body);
    }

    @Override
    public void setBio(String bio) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("bio", bio);

        api.getAPI().post("profile/me", body);
    }

    @Override
    public List<Notification> getNotifications() {
        JSONArray notificationsraw = new JSONArray(api.getAPI().get("/notifications"));
        ArrayList<Notification> notifications = new ArrayList<>();
        for(int i = 0; i < notificationsraw.length(); i++) {
            notifications.add(new NotificationImpl((JSONObject) notificationsraw.get(i), api));
        }
        return notifications;
    }

    @Override
    public List<Report> getReports() {
        JSONArray reportResponse = new JSONArray(api.getAPI().get("/reports"));
        ArrayList<Report> reports = new ArrayList<>();
        for(int i = 0; i < reportResponse.length(); i++) {
            reports.add(new ReportImpl((JSONObject) reportResponse.get(i), api));
        }
        return reports;
    }

    @Override
    public Report getReport(int id) {
        return new ReportImpl(new JSONObject(api.getAPI().get("/report/" + id)), api);
    }

    @Override
    public int getNotificationCount() {
        return new JSONObject(api.getAPI().get("/notifications/count")).getInt("count");
    }

    @Override
    public List<UserIntegration> getIntegrations() {
        List<UserIntegration> integrations = new ArrayList<>();
        JSONObject integration = new JSONObject(api.getAPI().get("/integrations"));
        for(String service : integration.keySet()) {
            JSONArray datas = integration.getJSONArray(service);
            for(Object data : datas) {
                integrations.add(new UserIntegrationImpl(service, (String) data));
            }
        }
        return integrations;
    }
}
