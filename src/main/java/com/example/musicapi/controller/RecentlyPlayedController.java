package com.example.musicapi.controller;

import com.example.musicapi.model.RecentlyPlayed;
import com.example.musicapi.service.RecentlyPlayedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recentlyPlayed")
public class RecentlyPlayedController {

    @Autowired
    private RecentlyPlayedService recentlyPlayedService;

    @GetMapping
    public ResponseEntity<List<RecentlyPlayed>> getAllRecentlyPlayed() {
        List<RecentlyPlayed> recentlyPlayed = recentlyPlayedService.getAllRecentlyPlayed();
        return new ResponseEntity<>(recentlyPlayed, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecentlyPlayed> getRecentlyPlayedById(@PathVariable String id) {
        Optional<RecentlyPlayed> recentlyPlayed = recentlyPlayedService.getRecentlyPlayedById(id);
        return recentlyPlayed.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RecentlyPlayed> createRecentlyPlayed(@RequestBody RecentlyPlayed recentlyPlayed) {
        RecentlyPlayed createdRecentlyPlayed = recentlyPlayedService.createRecentlyPlayed(recentlyPlayed);
        return new ResponseEntity<>(createdRecentlyPlayed, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecentlyPlayed> updateRecentlyPlayed(@PathVariable String id, @RequestBody RecentlyPlayed updatedRecentlyPlayed) {
        Optional<RecentlyPlayed> updatedRecentlyPlayedOptional = recentlyPlayedService.updateRecentlyPlayed(id, updatedRecentlyPlayed);
        return updatedRecentlyPlayedOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecentlyPlayed(@PathVariable String id) {
        recentlyPlayedService.deleteRecentlyPlayed(id);
        return ResponseEntity.noContent().build();
    }
}