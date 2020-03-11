package com.example.urstudyguide_migration.Common.Models;

public class MessageModelingClass {

    String from;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    String message;
    long time;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public MessageModelingClass() {

    }

    public MessageModelingClass(String from, String message, long time) {
        this.from = from;
        this.message = message;
        this.time = time;
    }
}
