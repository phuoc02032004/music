package com.example.musicapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "artists")
public class Artist {
    @Id
    private String id;
    private String name;
    private String description;
    private String genre;
    private String imageUrl;
    private String bio;

    // No-args constructor
    public Artist() {}

    // Full constructor
    public Artist(String id, String name, String description, String genre, String imageUrl, String bio) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.imageUrl = imageUrl;
        this.bio = bio;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
