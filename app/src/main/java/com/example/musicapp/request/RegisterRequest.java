package com.example.musicapp.request;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    public RegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}