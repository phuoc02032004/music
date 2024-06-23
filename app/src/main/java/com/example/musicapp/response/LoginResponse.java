package com.example.musicapp.response;

import com.example.musicapp.model.User;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("message")
    public String message;

    @SerializedName("user")
    public User user;

    public LoginResponse(String message, User user) {
        this.message = message;
        this.user = user;
    }
}