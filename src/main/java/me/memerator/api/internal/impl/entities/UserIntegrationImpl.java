package me.memerator.api.internal.impl.entities;

import me.memerator.api.client.entities.UserIntegration;

public class UserIntegrationImpl implements UserIntegration {
    private final String service, data;

    public UserIntegrationImpl(String service, String data) {
        this.service = service;
        this.data = data;
    }

    @Override
    public String getService() {
        return service;
    }

    @Override
    public String getData() {
        return data;
    }
}
