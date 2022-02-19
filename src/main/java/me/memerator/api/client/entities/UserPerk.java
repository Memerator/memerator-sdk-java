package me.memerator.api.client.entities;

public enum UserPerk {
    // A verified user.
    VERIFIED,

    // A user with Pro.
    PRO,

    // A Memerator staff member
    STAFF,

    // A user who helped translate Memerator.
    TRANSLATOR,

    // A service account is an account specifically for doing things for Memerator, and is not human owned.
    // They don't have any memes
    SERVICE,

    // A Memerator Founder.
    FOUNDER
}
