package me.memerator.api.client.entities;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A profile is a user, but has more details.
 * Usually, only the authenticated user can see these.
 */
public interface Profile extends User {
    /**
     * @return the time when your Pro subscription expires, if you have one
     */
    @Nullable
    String getProExpirationDate();

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
    void setUsername(String username);

    /**
     * Set your bio
     * @param bio the new bio to set
     */
    void setBio(String bio);

    /**
     * @return your notifications
     */
    List<Notification> getNotifications();

    /**
     * @return your reports
     */
    List<Report> getReports();

    /**
     * Gets a specific report by ID
     * @param id the report id
     * @return the report
     */
    Report getReport(int id);

    /**
     * @return your amount of notifications
     */
    int getNotificationCount();

    /**
     * Returns as a UserIntegrations object, from there you can get each integration type.
     * @return the integrations.
     */
    List<UserIntegration> getIntegrations();
}
