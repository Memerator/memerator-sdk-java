package me.memerator.api.client;

import me.memerator.api.internal.impl.MemeratorAPIImpl;

/**
 * Builder for {@link MemeratorAPI}.
 *
 * Use {@link #build()} to create a new instance.
 */
public class MemeratorAPIBuilder {
    private String token;

    public MemeratorAPIBuilder(String token) {
        this.token = token;
    }

    /**
     * Sets the token to use for the API.
     *
     * @param token The token to use.
     * @return This builder, for chaining.
     */
    public MemeratorAPIBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * Builds a new {@link MemeratorAPIBuilder} instance with the given token.
     *
     * @param token The token to use.
     * @return A new {@link MemeratorAPIBuilder} instance.
     */
    public static MemeratorAPIBuilder create(String token) {
        return new MemeratorAPIBuilder(token);
    }

    /**
     * Builds a new {@link MemeratorAPI} instance.
     *
     * @return A new {@link MemeratorAPI} instance.
     */
    public MemeratorAPI build() {
        if (token == null) {
            throw new IllegalArgumentException("Token must be set.");
        }

        return new MemeratorAPIImpl(token);
    }
}
