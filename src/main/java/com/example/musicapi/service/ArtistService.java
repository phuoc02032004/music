package com.example.musicapi.service;

import com.example.musicapi.model.Artist;
import com.example.musicapi.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public Optional<Artist> getArtistById(String id) {
        return artistRepository.findById(id);
    }

    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public Optional<Artist> updateArtist(String id, Artist updatedArtist) {
        return artistRepository.findById(id)
                .map(existingArtist -> {
                    existingArtist.setName(updatedArtist.getName());
                    existingArtist.setDescription(updatedArtist.getDescription());
                    existingArtist.setGenre(updatedArtist.getGenre());
                    existingArtist.setImageUrl(updatedArtist.getImageUrl());
                    existingArtist.setBio(updatedArtist.getBio());
                    return artistRepository.save(existingArtist);
                });
    }

    public void deleteArtist(String id) {
        artistRepository.deleteById(id);
    }
}