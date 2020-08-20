import me.memerator.api.MemeratorAPI;
import me.memerator.api.entity.Age;
import me.memerator.api.object.Meme;
import org.junit.Test;

import static org.junit.Assert.*;

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
    }
}
