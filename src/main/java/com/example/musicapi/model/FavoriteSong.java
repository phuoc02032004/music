package com.example.musicapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "favoritesongs")
public class FavoriteSong {
    @Id
    private String id;

    @DocumentReference
    private Song song;

    // Constructor
    public FavoriteSong() {
    }

    public FavoriteSong(String id, Song song) {
        this.id = id;
        this.song = song;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}