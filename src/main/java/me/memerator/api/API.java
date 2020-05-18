package me.memerator.api;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class API {
    public static final String baseUrl = "https://api.memerator.me/v1/";
    public API() {

    }

    public JSONObject get(String path, String key) {
        final JSONObject[] json = new JSONObject[1];
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .get()
                .addHeader("Authorization", key)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                throw new java.lang.RuntimeException("API Call failed! See above for stack trace");
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    json[0] = new JSONObject(responseBody.string());
                }
            }
        });
        return json[0];
    }

    public JSONObject post(String path, String key, HashMap<String, Object> args) {
        final JSONObject[] json = new JSONObject[1];
        FormBody.Builder bodyArgs = new FormBody.Builder();
        for(Map.Entry<String, Object> entry : args.entrySet()) {
            bodyArgs.add(entry.getKey(), (String) entry.getValue());
        }
        RequestBody formBody = bodyArgs.build();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .post(formBody)
                .addHeader("Authorization", key)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                throw new java.lang.RuntimeException("API Call failed! See above for stack trace");
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    json[0] = new JSONObject(responseBody.string());
                }
            }
        });
        return json[0];
    }
}
