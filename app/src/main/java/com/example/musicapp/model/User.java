package com.example.musicapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("id")
    public String id;

    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    @SerializedName("name")
    public String name;

    @SerializedName("email")
    public String email;

    @SerializedName("createdAt")
    public String createdAt;

    @SerializedName("subscriptionType")
    public String subscriptionType;

    @SerializedName("playlists")
    public List<Playlist> playlists;

    @SerializedName("likedSongs")
    public List<String> likedSongs;

    @SerializedName("recentlyPlayed")
    public List<RecentlyPlayed> recentlyPlayed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<String> getLikedSongs() {
        return likedSongs;
    }

    public void setLikedSongs(List<String> likedSongs) {
        this.likedSongs = likedSongs;
    }

    public List<RecentlyPlayed> getRecentlyPlayed() {
        return recentlyPlayed;
    }

    public void setRecentlyPlayed(List<RecentlyPlayed> recentlyPlayed) {
        this.recentlyPlayed = recentlyPlayed;
    }
}