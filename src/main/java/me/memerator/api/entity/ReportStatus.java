package me.memerator.api.entity;

public enum ReportStatus {
    /**
     * An open report, unhandled.
     */
    OPEN(0),

    /**
     * Sets the max age to 2. Allows family friendly and teen.
     */
    ASSIGNED(1),

    /**
     * Sets the max age to 4. Allows all memes on Memerator.
     */
    RESOLVED(2);

    private final int status;

    ReportStatus(int status) {
        this.status = status;
    }

    public int getAsInt() {
        return status;
    }

    public static ReportStatus fromInt(int status) {
        switch (status) {
            case 0:
                return OPEN;
            case 1:
                return ASSIGNED;
            case 2:
                return RESOLVED;
        }
        return null;
    }

    public String toString() {
        switch(status) {
            case (0):
                return "Open";
            case (1):
                return "Resolved";
            case (2):
                return "Closed";
            default:
                return "Unknown";
        }
    }
}
