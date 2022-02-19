package me.memerator.api.client.entities;

public class UserIntegration {
    private final String service, data;

    public UserIntegration(String service, String data) {
        this.service = service;
        this.data = data;
    }

    public String getService() {
        return service;
    }

    public String getData() {
        return data;
    }
}
