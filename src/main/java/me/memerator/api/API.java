package me.memerator.api;

import me.memerator.api.errors.*;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class API {
    private final OkHttpClient client;
    private final String key;
    public static final String baseUrl = "https://api.memerator.me/v1/";

    public API(String apiKey) {
        client = new OkHttpClient();
        this.key = apiKey;
    }

    public String get(String path) {
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .get()
                .addHeader("Authorization", key)
                .build();

        return performRequest(request);
    }

    public String post(String path, HashMap<String, Object> args) {
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .post(bodyFromHash(args))
                .addHeader("Authorization", key)
                .build();

        return performRequest(request);
    }

    public String put(String path, HashMap<String, Object> args) {
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .put(bodyFromHash(args))
                .addHeader("Authorization", key)
                .build();

        return performRequest(request);
    }

    public String patch(String path, HashMap<String, Object> args) {
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .patch(bodyFromHash(args))
                .addHeader("Authorization", key)
                .build();

        return performRequest(request);
    }

    public String delete(String path) {
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .delete()
                .addHeader("Authorization", key)
                .build();

        return performRequest(request);
    }

    public RequestBody bodyFromHash(HashMap<String, Object> args) {
        FormBody.Builder bodyArgs = new FormBody.Builder();
        for(Map.Entry<String, Object> entry : args.entrySet()) {
            bodyArgs.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return bodyArgs.build();
    }

    public String performRequest(Request request) {
        try (Response response = client.newCall(request).execute()) {
            switch(response.code()) {
                case 400:
                    throw new IllegalArgumentException("1 or more arguments were invalid");
                case 401:
                    throw new InvalidToken("Your API token failed authentication.");
                case 403:
                    throw new Unauthorized("Your API token is valid, however it can't access this object.");
                case 404:
                    throw new NotFound("That object or endpoint doesn't exist!");
                case 429:
                    throw new RateLimited("You have reached the rate limit!");
                case 500:
                    throw new InternalServerError("A server side error occurred while performing this request. Please try again later!");
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
