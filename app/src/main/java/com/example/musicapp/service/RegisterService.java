package com.example.musicapp.service;

import com.example.musicapp.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("api/users/register")
    Call<User> register(@Body User user);
}