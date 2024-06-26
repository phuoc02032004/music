package com.example.musicapi.service;

import com.example.musicapi.model.Song;
import com.example.musicapi.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;


    @Value("${upload.folder}")
    private String uploadFolder;

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Optional<Song> getSongById(String id) {
        return songRepository.findById(id);
    }

    public Song createSong(Song song) {
        return songRepository.save(song);
    }

    public Optional<Song> updateSong(String id, Song updatedSong) {
        if (songRepository.existsById(id)) {
            updatedSong.setId(id);
            return Optional.of(songRepository.save(updatedSong));
        } else {
            return Optional.empty();
        }
    }

    public void deleteSong(String id) {
        songRepository.deleteById(id);
    }

    public Song uploadFile(String songId, MultipartFile file) throws IOException {
        Optional<Song> song = songRepository.findById(songId);
        if (song.isPresent()) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            // Sử dụng File.getCanonicalPath() để xác định đường dẫn chính xác
            String filePath = new File(uploadFolder).getCanonicalPath() + File.separator + fileName;

            // Tạo thư mục uploads/music nếu chưa tồn tại
            File uploadDir = new File(uploadFolder);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            file.transferTo(new File(filePath));

            song.get().setFileName(fileName);
            song.get().setFilePath(filePath);
            return songRepository.save(song.get());
        } else {
            return null;
        }
    }

    public Song uploadImage(String songId, MultipartFile file) throws IOException {
        Optional<Song> song = songRepository.findById(songId);
        if (song.isPresent()) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = new File(uploadFolder + File.separator + "images").getCanonicalPath() + File.separator + fileName;

            File uploadDir = new File(uploadFolder + File.separator + "images");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            file.transferTo(new File(filePath));

            song.get().setImageFileName(fileName);
            song.get().setImageFilePath(filePath);
            return songRepository.save(song.get());
        } else {
            return null;
        }
    }
}