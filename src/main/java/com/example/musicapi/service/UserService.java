package com.example.musicapi.service;

import com.example.musicapi.model.User;
import com.example.musicapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> updateUser(String id, User updatedUser) {
        if (userRepository.existsById(id)) {
            updatedUser.setId(id);
            return Optional.of(userRepository.save(updatedUser));
        } else {
            return Optional.empty();
        }
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public Optional<User> login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDate.now());
        }

        return userRepository.save(user);
    }

    // Upload ảnh người dùng
    public User uploadImage(String id, MultipartFile file) throws IOException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            // Lưu file ảnh
            String fileName = file.getOriginalFilename();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            String uniqueFileName = UUID.randomUUID().toString() + "." + fileExtension;

            // Lưu ảnh vào thư mục 'uploads/users/'
            // Cần cấu hình đường dẫn lưu ảnh
            String uploadDir = "uploads/users/" + id;
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath);

            // Cập nhật thông tin ảnh cho người dùng
            User updatedUser = user.get();
            updatedUser.setFileName(uniqueFileName);
            updatedUser.setImageFileName(fileName);
            updatedUser.setImageFilePath(filePath.toString()); // Lưu đường dẫn đầy đủ
            userRepository.save(updatedUser);
            return updatedUser;
        }
        return null;
    }
}