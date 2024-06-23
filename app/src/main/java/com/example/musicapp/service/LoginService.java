package com.example.musicapp.service;

import com.example.musicapp.request.LoginRequest;
import com.example.musicapp.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("api/users/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}