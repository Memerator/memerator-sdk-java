import me.memerator.api.MemeratorAPI;
import me.memerator.api.entity.UserPerk;
import me.memerator.api.object.Profile;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    public void proTest() {
        Profile me = api.getProfile();
        assertTrue(me.isProActive());
        assertEquals(Color.decode("#ea8de9"), me.getNameColor());
        assertEquals(Color.decode("#85aafc"), me.getBackgroundColor());
        assertEquals("https://cdn.memerator.me/bg_476488167042580481_hN3sT.png", me.getBackgroundUrl());
    }
}
