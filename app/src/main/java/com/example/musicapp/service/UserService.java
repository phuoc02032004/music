package com.example.musicapp.service;

import com.example.musicapp.request.ChangePasswordRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @PUT("/api/users/{userId}/change-password")
    Call<Void> changePassword(@Path("userId") String userId, @Body ChangePasswordRequest request);
}
