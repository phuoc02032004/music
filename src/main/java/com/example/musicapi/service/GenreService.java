package com.example.musicapi.service;

import com.example.musicapi.model.Genre;
import com.example.musicapi.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(String id) {
        return genreRepository.findById(id).orElse(null);
    }

    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre updateGenre(String id, Genre genre) {
        Genre existingGenre = genreRepository.findById(id).orElse(null);
        if (existingGenre != null) {
            existingGenre.setName(genre.getName());
            existingGenre.setDescription(genre.getDescription());
            return genreRepository.save(existingGenre);
        }
        return null;
    }

    public void deleteGenre(String id) {
        genreRepository.deleteById(id);
    }
}