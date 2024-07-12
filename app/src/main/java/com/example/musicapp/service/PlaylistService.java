package com.example.musicapp.service;

import com.example.musicapp.model.Playlist;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PlaylistService {
    @GET("api/playlists")
    Call<List<Playlist>> getAllPlaylists();

    @GET("api/playlists/{id}")
    Call<Playlist> getPlaylistById(@Path("id") String id);

    @POST("api/playlists")
    Call<Playlist> createPlaylist(@Body Playlist playlist);

    @PUT("api/playlists/{id}")
    Call<Playlist> updatePlaylist(@Path("id") String id, @Body Playlist playlist);

    @DELETE("api/playlists/{id}")
    Call<Void> deletePlaylist(@Path("id") String id);
}
