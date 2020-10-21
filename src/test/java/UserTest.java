import me.memerator.api.MemeratorAPI;
import me.memerator.api.entity.UserPerk;
import me.memerator.api.object.Profile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {
    public static MemeratorAPI api = new MemeratorAPI(System.getenv("API_KEY"));

    @Test
    public void perkTest() {
        Profile me = api.getProfile();
        List<UserPerk> perk = me.getUserPerks();
        assertTrue(perk.contains(UserPerk.VERIFIED));
        assertTrue(perk.contains(UserPerk.PRO));
        assertTrue(perk.contains(UserPerk.STAFF));
        assertTrue(perk.contains(UserPerk.FOUNDER));
        assertFalse(perk.contains(UserPerk.SERVICE));
        assertFalse(perk.contains(UserPerk.TRANSLATOR));
    }
}
