package me.memerator.api.internal.impl.entities;

import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.entities.Report;
import me.memerator.api.client.entities.ReportStatus;
import me.memerator.api.client.entities.User;
import org.json.JSONObject;

/**
 * A report on Memerator
 */
public class ReportImpl implements Report {
    JSONObject values;
    MemeratorAPI api;

    public ReportImpl(JSONObject items, MemeratorAPI api) {
        values = items;
        this.api = api;
    }

    @Override
    public int getReportId() {
        return values.getInt("id");
    }

    @Override
    public int getStatusCode() {
        return values.getInt("status");
    }

    @Override
    public ReportStatus getStatus() {
        return ReportStatus.fromInt(values.getInt("status"));
    }

    @Override
    public String getAssociatedMemeId() {
        return values.getString("memeid");
    }

    @Override
    public String getReason() {
        return values.getJSONObject("message").getString("reason");
    }

    @Override
    public String getDescription() {
        return values.getJSONObject("message").getString("description");
    }

    @Override
    public User getAssignee() {
        if(values.get("assignee") == null) {
            return null;
        } else {
            return new UserImpl(values.getJSONObject("assignee"), api);
        }
    }

    @Override
    public String getStaffComment() {
        return values.getString("comment");
    }

    @Override
    public boolean isOpen() {
        return getStatusCode() == 0;
    }

    @Override
    public boolean isAssigned() {
        return getStatusCode() == 0;
    }

    @Override
    public boolean isClosed() {
        return getStatusCode() == 2;
    }
}

