package me.memerator.api.internal.requests;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RequestBuilder {
    public static final String baseUrl = "https://api.memerator.me/v1/";

    public static Request.Builder get(String path, @NotNull String key) {
        return new Request.Builder()
                .url(baseUrl + path)
                .get()
                .addHeader("Authorization", key);
    }

    public static Request.Builder post(String path, Map<String, Object> args, @NotNull String key) {
        return new Request.Builder()
                .url(baseUrl + path)
                .post(bodyFromHash(args))
                .addHeader("Authorization", key);
    }

    public static Request.Builder put(String path, Map<String, Object> args, @NotNull String key) {
        return new Request.Builder()
                .url(baseUrl + path)
                .put(bodyFromHash(args))
                .addHeader("Authorization", key);
    }

    public static Request.Builder patch(String path, Map<String, Object> args, @NotNull String key) {
        return new Request.Builder()
                .url(baseUrl + path)
                .patch(bodyFromHash(args))
                .addHeader("Authorization", key);
    }

    public static Request.Builder delete(String path, @NotNull String key) {
        return new Request.Builder()
                .url(baseUrl + path)
                .delete()
                .addHeader("Authorization", key);
    }

    public static RequestBody bodyFromHash(Map<String, Object> args) {
        FormBody.Builder bodyArgs = new FormBody.Builder();
        for(Map.Entry<String, Object> entry : args.entrySet()) {
            bodyArgs.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return bodyArgs.build();
    }
}
