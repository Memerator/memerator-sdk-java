import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.MemeratorAPIBuilder;
import me.memerator.api.client.entities.Age;
import me.memerator.api.client.entities.Meme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MemeTest {
    public static MemeratorAPI api = MemeratorAPIBuilder.create(System.getenv("API_KEY")).build();

    @Test
    public void getRandomMeme() {
        Meme meme = api.retrieveRandomMeme().complete();
        assertNotNull(meme);
    }

    @Test
    public void getFamilyFriendlyMeme() {
        Meme meme = api.retrieveRandomMeme(Age.FAMILY_FRIENDLY).complete();
        assertSame(meme.getAgeRating(), Age.FAMILY_FRIENDLY);
    }

    @Test
    public void getMemeInformation() {
        Meme meme = api.retrieveMeme("aaaaaaa").complete();
        assertNotNull(meme.getCaption());
        assertEquals("Chew", meme.getAuthor().getUsername());
        assertEquals("***__AAAAAAAA__***", meme.getCaption());
        assertFalse(meme.isDisabled());
        assertTrue(meme.getAverageRating() >= 1 && meme.getAverageRating() <= 5);
        assertTrue(meme.getTotalRatings() >= 1);
        assertEquals(Age.FAMILY_FRIENDLY, meme.getAgeRating());
        assertEquals("https://cdn.memerator.me/K7bLRy9.jpg", meme.getImageUrl());
        assertEquals("https://memerator.me/meme/" + meme.getMemeId(), meme.getMemeUrl());
        assertEquals(1550237341, meme.getTimestamp().toEpochMilli() / 1000);
        assertEquals("about 3 years", meme.getTimeAgoFormatted());
    }

    @Test
    public void rateMeme() {
        assertThrows(IllegalArgumentException.class, () -> api.retrieveMeme("aaaaaaa").complete().rate(6));
    }
}
