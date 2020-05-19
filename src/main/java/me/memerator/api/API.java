package me.memerator.api;

import me.memerator.api.errors.*;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class API {
    private static OkHttpClient client;
    private static String key;
    public static final String baseUrl = "https://api.memerator.me/v1/";

    public API(String apiKey) {
        client = new OkHttpClient();
        key = apiKey;
    }

    public JSONObject get(String path) throws NotFound, InvalidToken, RateLimited, Unauthorized, InternalServerError {
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .get()
                .addHeader("Authorization", key)
                .build();

        return performRequest(request);
    }

    public JSONObject post(String path, HashMap<String, Object> args) throws RateLimited, InvalidToken, NotFound, Unauthorized, InternalServerError {
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

        return performRequest(request);
    }

    public JSONObject performRequest(Request request) throws NotFound, InvalidToken, RateLimited, Unauthorized, InternalServerError {
        try (Response response = client.newCall(request).execute()) {
            switch(response.code()) {
                case 400:
                    throw new IllegalArgumentException("1 or more arguments were invalid");
                case 401:
                    throw new InvalidToken("Your API Token is invalid or can't access this endpoint or object.");
                case 403:
                    throw new Unauthorized("Your API token can't access this object or endpoint");
                case 404:
                    throw new NotFound("That object doesn't exist!");
                case 429:
                    throw new RateLimited("You have reached the rate limit!");
                case 500:
                    throw new InternalServerError("A server side error occured while performing this request. Please try again later!");
            }
            return new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
