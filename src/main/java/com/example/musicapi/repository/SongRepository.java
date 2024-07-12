package com.example.musicapi.repository;

import com.example.musicapi.model.Song;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends MongoRepository<Song, String> {
    List<Song> findByTitleContainingIgnoreCase(String query);
}