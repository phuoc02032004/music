package com.example.musicapi.controller;

import com.example.musicapi.model.Song;
import com.example.musicapi.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = songService.getAllSongs();
        if (songs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable String id) {
        Optional<Song> song = songService.getSongById(id);
        return song.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        Song createdSong = songService.createSong(song);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSong);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable String id, @RequestBody Song updatedSong) {
        Optional<Song> updatedSongOptional = songService.updateSong(id, updatedSong);
        return updatedSongOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable String id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<Song> uploadFile(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {
        Song updatedSong = songService.uploadFile(id, file);
        if (updatedSong != null) {
            return ResponseEntity.ok(updatedSong);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<String> downloadSong(@PathVariable String id) {
        Optional<Song> song = songService.getSongById(id);
        if (song.isPresent()) {
            String filePath = song.get().getFilePath();
            try {
                Resource resource = new UrlResource(filePath);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + song.get().getFileName() + "\"")
                        .body(String.valueOf(resource));
            } catch (MalformedURLException e) {
                return ResponseEntity.badRequest().body("Error downloading file: " + e.getMessage());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<Song> uploadImage(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {
        Song updatedSong = songService.uploadImage(id, file);
        if (updatedSong != null) {
            return ResponseEntity.ok(updatedSong);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/play")
    public ResponseEntity<String> playSong(@PathVariable String id) {
        Optional<Song> song = songService.getSongById(id);
        if (song.isPresent()) {
            // Kiểm tra xem file nhạc có tồn tại không
            String filePath = song.get().getFilePath();
            File file = new File(filePath);
            if (file.exists()) {
                // Sử dụng Spring's Resource để trả về file nhạc
                try {
                    Resource resource = new UrlResource(file.toURI());
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + song.get().getFileName() + "\"")
                            .body(String.valueOf(resource));
                } catch (MalformedURLException e) {
                    return ResponseEntity.badRequest().body("Error downloading file: " + e.getMessage());
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/next")
    public ResponseEntity<Song> nextSong(@PathVariable String id) {
        // 1. Tìm bài hát hiện tại dựa vào ID
        Optional<Song> currentSong = songService.getSongById(id);
        if (currentSong.isPresent()) {
            // 2. Lấy danh sách tất cả bài hát
            List<Song> allSongs = songService.getAllSongs();

            // 3. Tìm vị trí của bài hát hiện tại trong danh sách
            int currentIndex = allSongs.indexOf(currentSong.get());

            // 4. Tìm bài hát tiếp theo
            int nextIndex = (currentIndex + 1) % allSongs.size(); // Sử dụng modulo để quay vòng danh sách
            Song nextSong = allSongs.get(nextIndex);

            // 5. Trả về thông tin bài hát tiếp theo
            return ResponseEntity.ok(nextSong);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/previous")
    public ResponseEntity<Song> previousSong(@PathVariable String id) {
        // 1. Tìm bài hát hiện tại dựa vào ID
        Optional<Song> currentSong = songService.getSongById(id);
        if (currentSong.isPresent()) {
            // 2. Lấy danh sách tất cả bài hát
            List<Song> allSongs = songService.getAllSongs();

            // 3. Tìm vị trí của bài hát hiện tại tronig danh sách
            int currentIndex = allSongs.indexOf(currentSong.get());

            // 4. Tìm bài hát trước đó
            int previousIndex = (currentIndex - 1 + allSongs.size()) % allSongs.size(); // Sử dụng modulo để quay vòng danh sách
            Song previousSong = allSongs.get(previousIndex);

            // 5. Trả về thông tin bài hát trước đó
            return ResponseEntity.ok(previousSong);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}