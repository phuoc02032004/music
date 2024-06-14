package com.example.musicapi.service;

import com.example.musicapi.model.Song;
import com.example.musicapi.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Optional<Song> getSongById(String id) {
        return songRepository.findById(id);
    }

    public Song createSong(Song song) {
        return songRepository.save(song);
    }

    public Optional<Song> updateSong(String id, Song updatedSong) {
        return songRepository.findById(id)
                .map(existingSong -> {
                    existingSong.setTitle(updatedSong.getTitle());
                    existingSong.setArtist(updatedSong.getArtist());
                    existingSong.setAlbum(updatedSong.getAlbum());
                    existingSong.setDuration(updatedSong.getDuration());
                    existingSong.setAudioUrl(updatedSong.getAudioUrl());
                    existingSong.setLyrics(updatedSong.getLyrics());
                    existingSong.setGenres(updatedSong.getGenres());
                    existingSong.setPlayCount(updatedSong.getPlayCount());
                    existingSong.setLikes(updatedSong.getLikes());
                    return songRepository.save(existingSong);
                });
    }

    public void deleteSong(String id) {
        songRepository.deleteById(id);
    }
}