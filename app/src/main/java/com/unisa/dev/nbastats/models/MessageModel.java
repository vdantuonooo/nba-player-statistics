package com.unisa.dev.nbastats.models;

import com.google.gson.annotations.SerializedName;

public class MessageModel {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}