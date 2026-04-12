package com.HaritMitraBack.mitra.dto;

public class NotificationMessage {

    private String token;
    private String title;
    private String message;

    public NotificationMessage() {}

    public NotificationMessage(String token, String title, String message) {
        this.token = token;
        this.title = title;
        this.message = message;
    }

    public String getToken() { return token; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }

    public void setToken(String token) { this.token = token; }
    public void setTitle(String title) { this.title = title; }
    public void setMessage(String message) { this.message = message; }
}