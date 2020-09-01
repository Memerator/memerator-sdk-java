package me.memerator.api.object;

import me.memerator.api.MemeratorAPI;
import me.memerator.api.entity.ReportStatus;
import org.json.JSONObject;

/**
 * A report on Memerator
 */
public class Report {
    JSONObject values;
    MemeratorAPI api;

    public Report(JSONObject items, MemeratorAPI api) {
        values = items;
        this.api = api;
    }

    /**
     * @return the report ID.
     */
    public int getReportId() {
        return values.getInt("id");
    }

    /**
     * The status of the meme. Used to see where the report is.
     * Status 0 means it's open and unclaimed
     * Status 1 means it's claimed, but not settled.
     * Status 2 means it's resolved.
     * @return the status.
     * @deprecated
     * @see Report#getStatus()
     */
    public int getStatusCode() {
        return values.getInt("status");
    }

    /**
     * The status of the report.
     * @return the status
     */
    public ReportStatus getStatus() {
        return ReportStatus.fromInt(values.getInt("status"));
    }

    /**
     * @return the ID of the meme being reported.
     */
    public String getAssociatedMemeId() {
        return values.getString("memeid");
    }

    /**
     * @return the reason for the report.
     */
    public String getReason() {
        return values.getJSONObject("message").getString("reason");
    }

    /**
     * @return more detailed explanation
     */
    public String getDescription() {
        return values.getJSONObject("message").getString("description");
    }

    /**
     * @return the staff member assigned to this report, if one is assigned
     */
    public User getAssignee() {
        if(values.get("assignee") == null) {
            return null;
        } else {
            return new User(values.getJSONObject("assignee"), api);
        }
    }

    /**
     * @return the staff member's comment, if they responded.
     */
    public String getStaffComment() {
        return values.getString("comment");
    }

    /**
     * @return if the report is open
     * @deprecated
     * @see Report#getStatus()
     */
    public boolean isOpen() {
        return getStatusCode() == 0;
    }

    /**
     * @return if the report is assigned to someone
     * @deprecated
     * @see Report#getStatus()
     */
    public boolean isAssigned() {
        return getStatusCode() == 0;
    }

    /**
     * @return if the report is closed
     * @deprecated
     * @see Report#getStatus()
     */
    public boolean isClosed() {
        return getStatusCode() == 2;
    }
}

