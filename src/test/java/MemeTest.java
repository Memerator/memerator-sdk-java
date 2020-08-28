import me.memerator.api.MemeratorAPI;
import me.memerator.api.entity.Age;
import me.memerator.api.object.Meme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MemeTest {
    public static MemeratorAPI api = new MemeratorAPI(System.getenv("API_KEY"));

    @Test
    public void getRandomMeme() {
        Meme meme = api.getRandomMeme();
        assertNotNull(meme);
    }

    @Test
    public void getFamilyFriendlyMeme() {
        Meme meme = api.getRandomMeme(Age.FAMILY_FRIENDLY);
        assertSame(meme.getAgeRating(), Age.FAMILY_FRIENDLY);
    }

    @Test
    public void getMemeInformation() {
        Meme meme = api.getMeme("aaaaaaa");
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
        assertEquals("over 1 year", meme.getTimeAgoFormatted());
    }
}
