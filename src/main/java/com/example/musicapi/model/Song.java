package com.example.musicapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "songs")
public class Song {
    @Id
    private String id;
    private String title;
    private String audioFileName;
    private String audioFilePath;

    private String imageFileName;
    private String imageFilePath;

    @DocumentReference
    private Artist artist;

    @DocumentReference
    private Album album;

    private int duration; // In seconds
    private String lyrics;
    private List<String> genres;
    private int playCount;
    private int likes;
    private boolean isLooping; // Thêm thuộc tính isLooping
    @DocumentReference
    private List<FavoriteSong> favoriteSongs;

    public Song(List<FavoriteSong> favoriteSongs) {
        this.favoriteSongs = favoriteSongs;
    }

    public List<FavoriteSong> getFavoriteSongs() {
        return favoriteSongs;
    }

    public void setFavoriteSongs(List<FavoriteSong> favoriteSongs) {
        this.favoriteSongs = favoriteSongs;
    }

    // Constructor không tham số
    public Song() {
    }

    // Constructor đầy đủ
    public Song(String id, String title, Artist artist, Album album, int duration, String audioUrl, String lyrics, List<String> genres, int playCount, int likes, boolean isLooping, String audioFileName, String audioFilePath, String imageFileName, String imageFilePath) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.audioFileName = audioFileName;
        this.audioFilePath = audioFilePath;
        this.imageFileName = imageFileName;
        this.imageFilePath = imageFilePath;
        this.lyrics = lyrics;
        this.genres = genres;
        this.playCount = playCount;
        this.likes = likes;
        this.isLooping = isLooping;
    }

    // Getters and Setters
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

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLooping() {
        return isLooping;
    }

    public void setLooping(boolean looping) {
        this.isLooping = looping;
    }

    public String getAudioFileName() {
        return audioFileName;
    }

    public void setAudioFileName(String audioFileName) {
        this.audioFileName = audioFileName;
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
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