package com.example.musicapi.service;

import com.example.musicapi.model.RecentlyPlayed;
import com.example.musicapi.repository.RecentlyPlayedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecentlyPlayedService {

    @Autowired
    private RecentlyPlayedRepository recentlyPlayedRepository;

    public List<RecentlyPlayed> getAllRecentlyPlayed() {
        return recentlyPlayedRepository.findAll();
    }

    public Optional<RecentlyPlayed> getRecentlyPlayedById(String id) {
        return recentlyPlayedRepository.findById(id);
    }

    public RecentlyPlayed createRecentlyPlayed(RecentlyPlayed recentlyPlayed) {
        return recentlyPlayedRepository.save(recentlyPlayed);
    }

    public Optional<RecentlyPlayed> updateRecentlyPlayed(String id, RecentlyPlayed updatedRecentlyPlayed) {
        return recentlyPlayedRepository.findById(id)
                .map(existingRecentlyPlayed -> {
                    existingRecentlyPlayed.setSongId(updatedRecentlyPlayed.getSongId());
                    existingRecentlyPlayed.setPlayedAt(updatedRecentlyPlayed.getPlayedAt());
                    return recentlyPlayedRepository.save(existingRecentlyPlayed);
                });
    }

    public void deleteRecentlyPlayed(String id) {
        recentlyPlayedRepository.deleteById(id);
    }
}