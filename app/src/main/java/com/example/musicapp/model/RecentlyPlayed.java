package com.example.musicapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
public class RecentlyPlayed {
    @SerializedName("songId")
    public String songId;

    @SerializedName("playedAt")
    public String playedAt;

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(String playedAt) {
        this.playedAt = playedAt;
    }
}
