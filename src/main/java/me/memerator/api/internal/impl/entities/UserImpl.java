package me.memerator.api.internal.impl.entities;

import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.entities.Meme;
import me.memerator.api.client.entities.User;
import me.memerator.api.client.entities.UserPerk;
import me.memerator.api.internal.requests.RequestBuilder;
import me.memerator.api.internal.requests.Requester;
import okhttp3.Request;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Color;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class UserImpl implements User {
    JSONObject values;
    MemeratorAPI api;

    public UserImpl(JSONObject items, MemeratorAPI api) {
        values = items;
        this.api = api;
    }

    @Override
    public String getUsername() {
        return values.getString("username");
    }

    @Override
    public long getId() {
        return values.getLong("id");
    }

    @Override
    public String getBio() {
        try {
            return values.getString("bio");
        } catch (org.json.JSONException e) {
            return null;
        }
    }

    @Override
    public int getFollowerCount() {
        return values.getJSONObject("stats").getInt("followers");
    }

    @Override
    public int getFollowingCount() {
        return values.getJSONObject("stats").getInt("following");
    }

    @Override
    public int getMemeCount() {
        return values.getJSONObject("stats").getInt("memes");
    }

    @Override
    public boolean isVerified() {
        return values.getJSONObject("perks").getBoolean("verified");
    }

    @Override
    public boolean isStaff() {
        return values.getJSONObject("perks").getBoolean("staff");
    }

    @Override
    public boolean isTranslator() {
        return values.getJSONObject("perks").getBoolean("translator");
    }

    @Override
    public boolean isPro() {
        return values.getJSONObject("perks").getBoolean("pro");
    }

    @Override
    public boolean isService() {
        return values.getJSONObject("perks").getBoolean("service");
    }

    @Override
    public boolean isFounder() {
        return values.getJSONObject("perks").getBoolean("founder");
    }

    @Override
    public List<UserPerk> getUserPerks() {
        List<UserPerk> perks = new ArrayList<>();
        for (String object : values.getJSONObject("perks").keySet()) {
            boolean has = values.getJSONObject("perks").getBoolean(object);
            if (has) {
                perks.add(UserPerk.valueOf(object.toUpperCase()));
            }
        }
        return perks;
    }

    @Override
    public boolean hasPerk(UserPerk perk) {
        return getUserPerks().contains(perk);
    }

    @Override
    public String getProfileUrl() {
        return values.getString("permalink");
    }

    @Override
    public Instant getJoinTimestamp() {
        return Instant.ofEpochSecond(values.getLong("joined_epoch_seconds"));
    }

    @Override
    public Requester<List<Meme>> retrieveMemes() {
        if (hasPerk(UserPerk.SERVICE)) throw new IllegalArgumentException("User is a service, cannot retrieve memes");

        Request request = RequestBuilder.get("/profile/" + getId() + "/memes", api.getToken()).build();

        Function<String, List<Meme>> function = (String object) -> {
            JSONArray response = new JSONArray(object);
            List<Meme> memes = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                memes.add(new MemeImpl((JSONObject) response.get(i), api));
            }
            return memes;
        };

        return new Requester<>(api, request, function);
    }

    @Override
    @Nullable
    @Deprecated
    public String getProStartDate() {
        if (hasPerk(UserPerk.PRO)) {
            return values.getJSONObject("pro").getString("since");
        } else {
            return null;
        }
    }

    @Override
    @Nullable
    public OffsetDateTime getProSince() {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSX");
        if (hasPerk(UserPerk.PRO)) {
            return OffsetDateTime.parse(values.getString("timestamp"), inputFormat);
        } else {
            return null;
        }
    }

    @Override
    public boolean isProActive() {
        if (hasPerk(UserPerk.PRO)) {
            return values.getJSONObject("pro").getBoolean("active");
        } else {
            return false;
        }
    }

    @Override
    @Nullable
    public Color getNameColor() {
        if (hasPerk(UserPerk.PRO)) {
            String color = values.getJSONObject("pro").getString("name_color");
            if (color == null) {
                return null;
            }
            return Color.decode("#" + color);
        } else {
            return null;
        }
    }

    @Override
    @Nullable
    public Color getBackgroundColor() {
        if (hasPerk(UserPerk.PRO)) {
            String color = values.getJSONObject("pro").getString("background_color");
            if (color == null) {
                return null;
            }
            return Color.decode("#" + color);
        } else {
            return null;
        }
    }

    @Override
    @Nullable
    public String getBackgroundUrl() {
        if (hasPerk(UserPerk.PRO)) {
            return values.getJSONObject("pro").getString("background_url");
        } else {
            return null;
        }
    }
}
