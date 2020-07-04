package me.memerator.api.entity;

public enum MaxAge {
    /**
     * Sets the max age to 1. Family friendly memes only.
     */
    FAMILY_FRIENDLY(1),

    /**
     * Sets the max age to 2. Allows family friendly and teen.
     */
    TEEN(2),

    /**
     * Sets the max age to 4. Allows all memes on Memerator.
     */
    MATURE(4);

    public final int age;

    MaxAge(int age) {
        this.age = age;
    }

    public int getAgeInt() {
        return age;
    }

    public static MaxAge fromAge(int age) {
        switch (age) {
            case 1:
                return FAMILY_FRIENDLY;
            case 2:
                return TEEN;
            case 4:
                return MATURE;
        }
        return null;
    }
}
