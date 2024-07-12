package com.example.musicapi.controller;

import com.example.musicapi.model.Song;
import com.example.musicapi.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @Value("${upload.folder}")
    private String uploadFolder;


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

    @PostMapping("/{id}/upload-audio")
    public ResponseEntity<Song> uploadFile(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {
        Song updatedSong = songService.uploadFile(id, file);
        if (updatedSong != null) {
            return ResponseEntity.ok(updatedSong);
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
    @GetMapping("/{id}/audio")
    public ResponseEntity<Resource> downloadAudio(@PathVariable String id) throws MalformedURLException {
        Optional<Song> song = songService.getSongById(id);
        if (song.isPresent()) {
            String fileName = song.get().getAudioFileName();
            String filePath = uploadFolder + File.separator + fileName;
            Path file = Paths.get(filePath);

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/stream")
    public ResponseEntity<byte[]> streamAudio(@PathVariable String id) throws IOException {
        Optional<Song> song = songService.getSongById(id);
        if (song.isPresent()) {
            String fileName = song.get().getAudioFileName();
            String filePath = uploadFolder + File.separator + fileName;
            Path file = Paths.get(filePath);

            if (Files.exists(file) && Files.isReadable(file)) {
                byte[] audioData = Files.readAllBytes(file); // Đọc dữ liệu âm thanh
                String mimeType = Files.probeContentType(file); // Xác định kiểu MIME
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                        .contentType(MediaType.parseMediaType(mimeType))
                        .body(audioData); // Trả về dữ liệu âm thanh
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/previous")
    public ResponseEntity<Song> getPreviousSong(@PathVariable String id) {
        Optional<Song> previousSong = songService.getPreviousSong(id);
        return previousSong.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/next")
    public ResponseEntity<Song> getNextSong(@PathVariable String id) {
        Optional<Song> nextSong = songService.getNextSong(id);
        return nextSong.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) throws IOException { // Thay đổi kiểu trả về
        Optional<Song> song = songService.getSongById(id);
        if (song.isPresent()) {
            String fileName = song.get().getImageFileName();
            String filePath = uploadFolder + File.separator + "images" + File.separator + fileName;
            Path file = Paths.get(filePath);

            if (Files.exists(file) && Files.isReadable(file)) {
                byte[] imageData = Files.readAllBytes(file); // Đọc dữ liệu hình ảnh thành mảng byte
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                        .contentType(MediaType.IMAGE_JPEG) // Hoặc MediaType.IMAGE_PNG
                        .body(imageData);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Song>> searchSongs(@RequestParam("query") String query) {
        List<Song> songs = songService.searchSongs(query); // Gọi phương thức searchSongs trong service
        if (songs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(songs); // Trả về danh sách bài hát tìm kiếm được
    }

}