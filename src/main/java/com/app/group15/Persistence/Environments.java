package com.app.group15.Persistence;

public enum Environments {
    DEV("DEVINT"), TEST("TEST"), PROD("PRODUCTION");

    private final String environment;

    Environments(String environment) {
        this.environment = environment;
    }

    public String getEnvironment() {
        return environment;
    }

}
