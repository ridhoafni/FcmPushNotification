package com.dicoding.ridho_afni.fcmpushnotification.Model;

public class Notification {
    public String body;
    public String title;

    public Notification(String title, String body) {
        this.body = body;
        this.title = title;
    }

    public Notification() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
