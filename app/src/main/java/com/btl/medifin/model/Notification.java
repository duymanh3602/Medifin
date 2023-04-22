package com.btl.medifin.model;

public class Notification {

    private String title;
    private String content;
    private String date;
    private String img;

    public Notification() {
    }

    public Notification(String title, String content, String date, String img) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
