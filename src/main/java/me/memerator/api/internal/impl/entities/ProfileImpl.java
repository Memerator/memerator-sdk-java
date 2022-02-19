package me.memerator.api.internal.impl.entities;

import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.entities.Notification;
import me.memerator.api.client.entities.Profile;
import me.memerator.api.client.entities.Report;
import me.memerator.api.client.entities.UserIntegration;
import me.memerator.api.internal.requests.RequestBuilder;
import me.memerator.api.internal.requests.Requester;
import okhttp3.Request;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

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
    public Requester<Void> setUsername(String username) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("username", username);

        Request request = RequestBuilder.post("profile/me", body, api.getToken()).build();
        return new Requester<>(api, request, (s) -> null);
    }

    @Override
    public Requester<Void> setBio(String bio) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("bio", bio);

        Request request = RequestBuilder.post("profile/me", body, api.getToken()).build();
        return new Requester<>(api, request, (s) -> null);
    }

    @Override
    public Requester<List<Notification>> retrieveNotifications() {
        Request request = RequestBuilder.get("/notifications", api.getToken()).build();

        Function<String, List<Notification>> function = (s) -> {
            JSONArray response = new JSONArray(s);
            ArrayList<Notification> notifications = new ArrayList<>();
            for(Object notification : response) {
                notifications.add(new NotificationImpl((JSONObject) notification, api));
            }
            return notifications;
        };

        return new Requester<>(api, request, function);
    }

    @Override
    public Requester<List<Report>> retrieveReports() {
        Request request = RequestBuilder.get("/reports", api.getToken()).build();

        Function<String, List<Report>> function = (s) -> {
            JSONArray response = new JSONArray(s);
            ArrayList<Report> reports = new ArrayList<>();
            for(Object report : response) {
                reports.add(new ReportImpl((JSONObject) report, api));
            }
            return reports;
        };

        return new Requester<>(api, request, function);
    }

    @Override
    public Requester<Report> retrieveReport(int id) {
        Request request = RequestBuilder.get("/report/" + id, api.getToken()).build();
        Function<String, Report> function = (s) -> new ReportImpl(new JSONObject(s), api);

        return new Requester<>(api, request, function);
    }

    @Override
    public Requester<Integer> retrieveNotificationCount() {
        Request request = RequestBuilder.get("/notifications/count", api.getToken()).build();
        Function<String, Integer> function = (s) -> new JSONObject(s).getInt("count");

        return new Requester<>(api, request, function);
    }

    @Override
    public Requester<List<UserIntegration>> retrieveIntegrations() {
        Request request = RequestBuilder.get("/integrations", api.getToken()).build();

        Function<String, List<UserIntegration>> function = (s) -> {
            List<UserIntegration> integrations = new ArrayList<>();
            JSONObject integration = new JSONObject(s);
            for (String service : integration.keySet()) {
                JSONArray datas = integration.getJSONArray(service);
                for (Object data : datas) {
                    integrations.add(new UserIntegrationImpl(service, (String) data));
                }
            }
            return integrations;
        };

        return new Requester<>(api, request, function);
    }
}
