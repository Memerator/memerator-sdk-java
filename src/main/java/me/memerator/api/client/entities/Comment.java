package me.memerator.api.client.entities;

import java.time.Instant;

public interface Comment {
    /**
     * @return this comment's ID.
     */
    int getCommentId();

    /**
     * @return the actual comment contents.
     */
    String getCommentContent();

    /**
     * @return the timestamp of this comment
     */
    Instant getTimestamp();

    /**
     * @return the author of this comment.
     */
    User getAuthor();

    /**
     * @return the meme this comment was left on.
     */
    Meme getAssociatedMeme();

    /**
     * Deletes this comment, only works if you meet one of the following conditions
     * 1) You own the meme
     * 2) You made the comment
     */
    void delete();
}
