package me.memerator.api.client.entities;

/**
 * A report on Memerator
 */
public interface Report {
    /**
     * @return the report ID.
     */
    int getReportId();

    /**
     * The status of the meme. Used to see where the report is.
     * Status 0 means it's open and unclaimed
     * Status 1 means it's claimed, but not settled.
     * Status 2 means it's resolved.
     * @return the status.
     * @deprecated
     * @see Report#getStatus()
     */
    int getStatusCode();

    /**
     * The status of the report.
     * @return the status
     */
    ReportStatus getStatus();

    /**
     * @return the ID of the meme being reported.
     */
    String getAssociatedMemeId();

    /**
     * @return the reason for the report.
     */
    String getReason();

    /**
     * @return more detailed explanation
     */
    String getDescription();

    /**
     * @return the staff member assigned to this report, if one is assigned
     */
    User getAssignee();

    /**
     * @return the staff member's comment, if they responded.
     */
    String getStaffComment();

    /**
     * @return if the report is open
     * @deprecated
     * @see Report#getStatus()
     */
    boolean isOpen();

    /**
     * @return if the report is assigned to someone
     * @deprecated
     * @see Report#getStatus()
     */
    boolean isAssigned();

    /**
     * @return if the report is closed
     * @deprecated
     * @see Report#getStatus()
     */
    boolean isClosed();
}

