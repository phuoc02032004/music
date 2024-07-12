package com.example.musicapp.repository;

import com.example.musicapp.model.Song;
import com.example.musicapp.service.MusicService;
import com.example.musicapp.utils.APIClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongRepository {

    private MusicService musicService;

    public SongRepository() {
        this.musicService = APIClient.getMusicservice();
    }

    public void getSongImage(String songId, Callback<ResponseBody> callback) {
        Call<ResponseBody> imageCall = musicService.getImage(songId);
        imageCall.enqueue(callback);
    }

    public void getAudioUrl(String songId, Callback<ResponseBody> callback) { // Sửa thành Callback<ResponseBody>
        Call<ResponseBody> audioCall = musicService.getAudioUrl(songId); // Sửa thành Call<ResponseBody>
        audioCall.enqueue(callback);
    }
}