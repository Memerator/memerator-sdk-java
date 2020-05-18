package me.memerator.api;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class API {
    private static OkHttpClient client;
    public static final String baseUrl = "https://api.memerator.me/v1/";

    public API() {
        client = new OkHttpClient();
    }

    public JSONObject get(String path, String key) {
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .get()
                .addHeader("Authorization", key)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject post(String path, String key, HashMap<String, Object> args) {
        FormBody.Builder bodyArgs = new FormBody.Builder();
        for(Map.Entry<String, Object> entry : args.entrySet()) {
            bodyArgs.add(entry.getKey(), (String) entry.getValue());
        }
        RequestBody formBody = bodyArgs.build();

        Request request = new Request.Builder()
                .url(baseUrl + path)
                .post(formBody)
                .addHeader("Authorization", key)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
