package me.memerator.api.object;

import org.json.JSONObject;

/**
 * A report!
 */
public class Report {
    JSONObject values;

    public Report(JSONObject items) {
        values = items;
    }

    /**
     * @return [Integer] the report ID.
     */
    public int getReportId() {
        return values.getInt("id");
    }

    /**
     * The status of the meme. Used to see where the report is.
     * Status 0 means it's open and unclaimed
     * Status 1 means it's claimed, but not settled.
     * Status 2 means it's resolved.
     * @return [Integer] the status.
     */
    public int getStatusCode() {
        return values.getInt("status");
    }

    /**
     * @return [String] the ID of the meme being reported.
     */
    public String getAssociatedMemeId() {
        return values.getString("memeid");
    }

    /**
     * @return [String] the reason for the report.
     */
    public String getReason() {
        return values.getJSONObject("message").getString("reason");
    }

    /**
     * @return [String] more detailed explanation
     */
    public String getDescription() {
        return values.getJSONObject("message").getString("description");
    }

    /**
     * @return [User, nil] the staff member assigned to this report, if one is assigned
     */
    public User getAssignee() {
        if(values.get("assignee") == null) {
            return null;
        } else {
            return new User(values.getJSONObject("sender"));
        }
    }

    /**
     * @return [String, nil] the staff member's comment, if they responded.
     */
    public String getStaffComment() {
        return values.getString("comment");
    }

    /**
     * @return [true, false] if the report is open
     */
    public boolean isOpen() {
        return getStatusCode() == 0;
    }

    /**
     * @return [true, false] if the report is assigned to someone
     */
    public boolean isAssigned() {
        return getStatusCode() == 0;
    }

    /**
     * @return [true, false] if the report is closed
     */
    public boolean isClosed() {
        return getStatusCode() == 2;
    }
}

