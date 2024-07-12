package com.example.musicapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Playlist {
    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("songs")
    public List<Song> songs;

    @SerializedName("createdBy")
    public User createdBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Playlist(String id, String name, String description, List<Song> songs, User createdBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.songs = songs;
        this.createdBy = createdBy;
    }
}