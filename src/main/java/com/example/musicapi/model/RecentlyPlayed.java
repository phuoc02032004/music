package com.example.musicapi.model;

import java.util.Date;

public class RecentlyPlayed {
    private String songId;
    private Date playedAt;

    // Constructors
    public RecentlyPlayed() {
    }

    public RecentlyPlayed(String songId, Date playedAt) {
        this.songId = songId;
        this.playedAt = playedAt;
    }

    // Getters and Setters
    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public Date getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(Date playedAt) {
        this.playedAt = playedAt;
    }
}