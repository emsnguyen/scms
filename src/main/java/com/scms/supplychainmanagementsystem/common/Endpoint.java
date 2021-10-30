package com.scms.supplychainmanagementsystem.common;

public enum Endpoint {
    LOCALHOST("http://localhost:8080/api"),
    DEV("");
    public final String URL;

    Endpoint(String URL) {
        this.URL = URL;
    }
}
