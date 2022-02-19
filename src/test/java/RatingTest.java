import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.entities.Rating;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RatingTest {
    @Test
    public void myRatingTest() {
        MemeratorAPI api = new MemeratorAPI(System.getenv("API_KEY"));
        Rating rating = api.getMeme("aaaaaaa").getOwnRating();
        assertTrue(rating.getRating() > 0);
        assertNotNull(rating);
    }
}
