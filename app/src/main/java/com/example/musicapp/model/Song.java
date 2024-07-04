package com.example.musicapp.model;

import java.util.List;

import java.io.Serializable;

public class Song implements Serializable {
    private String id;
    private String title;
    // Thông tin file nhạc
    private String audioFileName; // Tên file nhạc
    private String audioFilePath; // Đường dẫn đến file nhạc

    // Thông tin file ảnh
    private String imageFileName; // Tên file ảnh
    private String imageFilePath; // Đường dẫn đến file ảnh
    private Artist artist;
    private Album album;
    private int duration;
    private String lyrics;
    private List<String> genres;
    private int playCount;
    private int likes;

    public Song(String id, String title, String audioFileName, String audioFilePath, String imageFileName, String imageFilePath, Artist artist, Album album, int duration, String lyrics, List<String> genres, int playCount, int likes) {
        this.id = id;
        this.title = title;
        this.audioFileName = audioFileName;
        this.audioFilePath = audioFilePath;
        this.imageFileName = imageFileName;
        this.imageFilePath = imageFilePath;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.lyrics = lyrics;
        this.genres = genres;
        this.playCount = playCount;
        this.likes = likes;
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

    public String getSongId() {
        return id; // Trả về id của bài hát
    }

    public String getImageUrl() {
        return imageFilePath; // Trả về imageFilePath của bài hát
    }
}