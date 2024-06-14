package com.example.musicapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "playlists")
public class Playlist {
    @Id
    private String id;
    private String name;
    private String description;

    @DocumentReference
    private List<Song> songs;

    @DocumentReference
    private User createdBy;

    // Constructors
    public Playlist() {
    }

    public Playlist(String id, String name, String description, List<Song> songs, User createdBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.songs = songs;
        this.createdBy = createdBy;
    }

    // Getters and Setters
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
}
