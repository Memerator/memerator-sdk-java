/*
 * Copyright 2022 Chew and Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Relocated from pw.chew.clickup4j.internal.requests
package me.memerator.api.internal.requests;

import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.client.errors.InternalServerError;
import me.memerator.api.client.errors.InvalidToken;
import me.memerator.api.client.errors.NotFound;
import me.memerator.api.client.errors.RateLimited;
import me.memerator.api.client.errors.Unauthorized;
import me.memerator.api.internal.impl.MemeratorAPIImpl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class Requester<T> {
    private final OkHttpClient client;
    private final Request request;
    private final Function<String, T> handler;

    public Requester(OkHttpClient client, Request request, Function<String, T> handler) {
        this.client = client;
        this.request = request;
        this.handler = handler;
    }

    // MemeratorAPI Begin: Add shorthand for instantiating Requester
    public Requester(MemeratorAPI api, Request request, Function<String, T> handler) {
        this.client = api.getClient();
        this.request = request;
        this.handler = handler;
    }
    // MemeratorAPI End

    public void queue(Consumer<T> onSuccess, Consumer<Throwable> onFailure) {
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                // MemeratorAPI Begin: Add response code checker
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
                // Memerator API End
                try {
                    String string = response.body().string();

                    T result = handler.apply(string);
                    onSuccess.accept(result);
                } catch (Exception e) {
                    onFailure.accept(e);
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                onFailure.accept(e);
            }
        });
    }

    /**
     * Queues this request. The {@code onSuccess} callback will be called when the request completes successfully.
     *
     * @param onSuccess The callback to call when the request completes successfully.
     */
    public void queue(Consumer<T> onSuccess) {
        queue(onSuccess, (Throwable t) -> {});
    }

    public T complete() {
        Call call = client.newCall(request);

        try(Response response = call.execute()) {
            return handler.apply(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
