package com.example.musicapi.service;

import com.example.musicapi.model.Song;
import com.example.musicapi.repository.SongRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;


    @Value("${upload.folder}")
    private String uploadFolder;
    private String audioFileName;


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

            // Tạo thư mục uploads nếu chưa tồn tại
            File uploadDir = new File(uploadFolder);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            file.transferTo(new File(filePath));

            // Lưu trữ tên file và đường dẫn tương đối vào database
            song.get().setAudioFileName(fileName);
            song.get().setAudioFilePath(fileName); // Lưu trữ chỉ tên tệp, không cần "music/"

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

    public Optional<Song> getPreviousSong(String id) {
        Optional<Song> currentSong = songRepository.findById(id);
        if (currentSong.isPresent()) {
            // Lấy danh sách tất cả bài hát
            List<Song> allSongs = songRepository.findAll();
            // Tìm vị trí của bài hát hiện tại trong danh sách
            int currentSongIndex = allSongs.indexOf(currentSong.get());
            // Nếu bài hát hiện tại là bài hát đầu tiên trong danh sách, trả về bài hát cuối cùng
            if (currentSongIndex == 0) {
                return Optional.of(allSongs.get(allSongs.size() - 1));
            } else {
                // Trả về bài hát trước đó
                return Optional.of(allSongs.get(currentSongIndex - 1));
            }
        } else {
            return Optional.empty();
        }
    }

    public Optional<Song> getNextSong(String id) {
        Optional<Song> currentSong = songRepository.findById(id);
        if (currentSong.isPresent()) {
            // Lấy danh sách tất cả bài hát
            List<Song> allSongs = songRepository.findAll();
            // Tìm vị trí của bài hát hiện tại trong danh sách
            int currentSongIndex = allSongs.indexOf(currentSong.get());
            // Nếu bài hát hiện tại là bài hát cuối cùng trong danh sách, trả về bài hát đầu tiên
            if (currentSongIndex == allSongs.size() - 1) {
                return Optional.of(allSongs.get(0));
            } else {
                // Trả về bài hát tiếp theo
                return Optional.of(allSongs.get(currentSongIndex + 1));
            }
        } else {
            return Optional.empty();
        }
    }

    public List<Song> searchSongs(String query) {
        // Sử dụng query để tìm kiếm các bài hát trong cơ sở dữ liệu
        // Ví dụ:
        return songRepository.findByTitleContainingIgnoreCase(query);
    }
}