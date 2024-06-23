package com.example.musicapp.utils;

import com.example.musicapp.service.LoginService;
import com.example.musicapp.service.RegisterService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// com.example.musicapp.service (hoặc thư mục tương tự)
public class APIClient {
    private static Retrofit retrofit;

    public static LoginService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/") // URL của API RESTful
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(LoginService.class);
    }

    public static RegisterService getRegisterService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/") // Thay thế bằng URL API của bạn
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(RegisterService.class);
    }
}