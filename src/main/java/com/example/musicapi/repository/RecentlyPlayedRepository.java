package com.example.musicapi.repository;

import com.example.musicapi.model.RecentlyPlayed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentlyPlayedRepository extends MongoRepository<RecentlyPlayed, String> {
}