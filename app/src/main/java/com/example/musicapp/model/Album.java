package com.example.musicapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Album {
    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("coverImageUrl")
    public String coverImageUrl;

    @SerializedName("releaseDate")
    public String releaseDate;

    @SerializedName("artist")
    public Artist artist; // Reference to the artist

    @SerializedName("songs")
    public List<Song> songs; // List of songs in this album

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }



    public Album(String id, String title, String description, String coverImageUrl, String releaseDate, Artist artist, List<Song> songs) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.releaseDate = releaseDate;
        this.artist = artist;
        this.songs = songs;
    }
}