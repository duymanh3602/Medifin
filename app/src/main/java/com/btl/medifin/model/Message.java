package com.btl.medifin.model;

public class Message {
    private String receiverId;
    private String senderId;
    private String msg;
    private boolean status; // false: no, true: seen;

    public Message() {
    }

    public Message(String receiverId, String senderId, String msg) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.msg = msg;
        this.status = false;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void seenMessage() {
        this.status = true;
    }
}
