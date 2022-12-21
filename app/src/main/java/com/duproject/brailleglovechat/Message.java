package com.duproject.brailleglovechat;



public class Message {

    private String message;
    private int type;

    public Message (String message, int type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }
}
