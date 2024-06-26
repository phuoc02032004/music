package com.example.musicapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String name;
    private String email;
    private LocalDate createdAt;
    private String subscriptionType;
    private List<Playlist> playlists;
    private List<String> likedSongs;
    private List<RecentlyPlayed> recentlyPlayed;
    private String fileName;
    private String imageFileName;
    private String imageFilePath;



    // No-args constructor
    public User() {}

    // Getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public List<String> getLikedSongs() {
        return likedSongs;
    }

    public List<RecentlyPlayed> getRecentlyPlayed() {
        return recentlyPlayed;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void setLikedSongs(List<String> likedSongs) {
        this.likedSongs = likedSongs;
    }

    public void setRecentlyPlayed(List<RecentlyPlayed> recentlyPlayed) {
        this.recentlyPlayed = recentlyPlayed;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }
}
