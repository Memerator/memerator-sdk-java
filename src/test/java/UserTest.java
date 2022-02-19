import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.entities.UserPerk;
import me.memerator.api.client.entities.Profile;
import me.memerator.api.internal.impl.MemeratorAPIImpl;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {
    public static MemeratorAPI api = new MemeratorAPIImpl(System.getenv("API_KEY"));

    @Test
    public void perkTest() {
        Profile me = api.retrieveProfile().complete();
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
        Profile me = api.retrieveProfile().complete();
        assertTrue(me.isProActive());
        assertEquals(Color.decode("#ea8de9"), me.getNameColor());
        assertEquals(Color.decode("#85aafc"), me.getBackgroundColor());
        assertEquals("https://cdn.memerator.me/bg_476488167042580481_hN3sT.png", me.getBackgroundUrl());
    }
}
