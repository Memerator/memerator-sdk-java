package me.memerator.api.client.entities;

import org.jetbrains.annotations.Nullable;

import java.awt.Color;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

public interface User {
    /**
     * @return the user's username
     */
    String getUsername();

    /**
     * @return the user's ID
     */
    long getId();

    /**
     * @return the user's bio or null if there isn't one
     */
    String getBio();

    /**
     * @return the amount of followers this user has
     */
    int getFollowerCount();

    /**
     * @return the amount of users this user is following
     */
    int getFollowingCount();

    /**
     * @return the amount of memes this user has
     */
    int getMemeCount();

    /**
     * @return the user's verification status
     * @deprecated
     * @see User#getUserPerks()
     */
    boolean isVerified();

    /**
     * @return the user's staff member status
     * @deprecated
     * @see User#getUserPerks()
     */
    boolean isStaff();

    /**
     * @return the user's translator status
     * @deprecated
     * @see User#getUserPerks()
     */
    boolean isTranslator();

    /**
     * @return the user's pro status
     * @deprecated
     * @see User#getUserPerks()
     */
    boolean isPro();

    /**
     * A service account is an account specifically for doing things for Memerator, and is not human owned.
     * They don't have any memes
     * @return the user's service status
     * @deprecated
     * @see User#getUserPerks()
     */
    boolean isService();

    /**
     * @return the user's founder status
     * @deprecated
     * @see User#getUserPerks()
     */
    boolean isFounder();

    /**
     * @return a list of this user's public perks
     */
    List<UserPerk> getUserPerks();

    /**
     * Shorthand for getUserPerks().contains(perk);
     * @param perk the perk to check
     * @return true/false if they have the specified perk
     */
    boolean hasPerk(UserPerk perk);

    /**
     * @return the user's profile link
     */
    String getProfileUrl();

    /**
     * @return the user's join timestamp
     */
    Instant getJoinTimestamp();

    /**
     * @return a list of this user's memes
     */
    List<Meme> getMemes();

    /**
     * @return the time when your Pro subscription started, if you have one
     * @see User#getProSince
     */
    @Nullable
    @Deprecated
    String getProStartDate();

    /**
     * The timestamp of the Pro start as an OffsetDateTime
     * @return the timestamp
     */
    @Nullable OffsetDateTime getProSince();

    /**
     * This method actually differs from User#isPro() as they are 2 separate checks.
     * They should always match, rarely is this value used outside of the Billing page.
     * @return if you have an active pro subscription
     */
    boolean isProActive();

    /**
     * Get the Pro name color for this user. If they're not pro, or don't have one set, this will be null.
     * @return the Pro name color
     */
    @Nullable Color getNameColor();

    /**
     * Get the Pro background color for this user. If they're not Pro, or don't have one set, this will be null.
     * @return the Pro background color
     */
    @Nullable Color getBackgroundColor();

    /**
     * The Pro background URL for this user. If they're not Pro, or don't have one set, this will be null.
     * @return the Pro Background URL.
     */
    @Nullable String getBackgroundUrl();
}
