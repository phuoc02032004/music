package com.example.musicapi.service;

import com.example.musicapi.model.Album;
import com.example.musicapi.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Optional<Album> getAlbumById(String id) {
        return albumRepository.findById(id);
    }

    public Album createAlbum(Album album) {
        return albumRepository.save(album);
    }

    public Optional<Album> updateAlbum(String id, Album updatedAlbum) {
        return albumRepository.findById(id)
                .map(existingAlbum -> {
                    existingAlbum.setTitle(updatedAlbum.getTitle());
                    existingAlbum.setDescription(updatedAlbum.getDescription());
                    existingAlbum.setCoverImageUrl(updatedAlbum.getCoverImageUrl());
                    existingAlbum.setReleaseDate(updatedAlbum.getReleaseDate());
                    existingAlbum.setArtist(updatedAlbum.getArtist());
                    existingAlbum.setSongs(updatedAlbum.getSongs());
                    return albumRepository.save(existingAlbum);
                });
    }

    public void deleteAlbum(String id) {
        albumRepository.deleteById(id);
    }
}