package com.example.musicapp.service;

import com.example.musicapp.model.RecentlyPlayed;
import com.example.musicapp.model.Song;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MusicService {

    @GET("api/songs/{id}")
    Call<Song> getSongById(@Path("id") String id);

    @GET("api/songs/{id}/imageSong")
    Call<String> getSongImage(@Path("id") String id);

    @GET("/api/songs")
    Call<List<Song>> getAllSongs();

    @GET("api/songs/{id}/stream")
    Call<ResponseBody> getAudioUrl(@Path("id") String id);

    @GET("api/songs/{id}/image")
    Call<ResponseBody> getImage(@Path("id") String id);

    @GET("api/songs/{id}/previous")
    Call<Song> getPreviousSong(@Path("id") String id);

    @GET("api/songs/{id}/next")
    Call<Song> getNextSong(@Path("id") String id);

    @GET("api/songs/search")
    Call<List<Song>> searchSongs(@Query("query") String query);

}