import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.entities.Rating;
import me.memerator.api.internal.impl.MemeratorAPIImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RatingTest {
    @Test
    public void myRatingTest() {
        MemeratorAPI api = new MemeratorAPIImpl(System.getenv("API_KEY"));
        Rating rating = api.retrieveMeme("aaaaaaa").complete().retrieveOwnRating().complete();
        assertTrue(rating.getRating() > 0);
        assertNotNull(rating);
    }
}
