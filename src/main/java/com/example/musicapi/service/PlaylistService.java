package com.example.musicapi.service;

import com.example.musicapi.model.Playlist;
import com.example.musicapi.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Optional<Playlist> getPlaylistById(String id) {
        return playlistRepository.findById(id);
    }

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Optional<Playlist> updatePlaylist(String id, Playlist updatedPlaylist) {
        return playlistRepository.findById(id)
                .map(existingPlaylist -> {
                    existingPlaylist.setName(updatedPlaylist.getName());
                    existingPlaylist.setDescription(updatedPlaylist.getDescription());
                    existingPlaylist.setSongs(updatedPlaylist.getSongs());
                    // ... (cập nhật các thuộc tính khác nếu cần)
                    return playlistRepository.save(existingPlaylist);
                });
    }

    public void deletePlaylist(String id) {
        playlistRepository.deleteById(id);
    }
}