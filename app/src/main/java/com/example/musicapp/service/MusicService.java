package com.example.musicapp.service;

import com.example.musicapp.model.RecentlyPlayed;
import com.example.musicapp.model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MusicService {

    @GET("api/songs/{id}")
    Call<Song> getSongById(@Path("id") String id);

    @GET("api/songs/{id}/imageSong")
    Call<String> getSongImage(@Path("id") String id);

    @GET("/api/songs")
    Call<List<Song>> getAllSongs();
}