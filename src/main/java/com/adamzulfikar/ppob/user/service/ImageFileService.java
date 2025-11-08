package com.adamzulfikar.ppob.user.service;

import com.adamzulfikar.ppob.common.config.JwtUtil;
import com.adamzulfikar.ppob.common.exception.BadRequestException;
import com.adamzulfikar.ppob.user.model.User;
import com.adamzulfikar.ppob.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class ImageFileService {
    @Autowired
    private final UserRepository userRepo;
    public ImageFileService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    private final String uploadDir = "C:\\Users\\admzu\\Documents\\Development\\spring-boot-project\\uploads"; // folder tempat file disimpan

    public User saveImage(MultipartFile file, String email) throws IOException {
        // Validasi format file JPG atau
        if (!file.getContentType().equalsIgnoreCase("image/jpg") && !file.getContentType().equalsIgnoreCase("image/jpeg")
                && !file.getContentType().equalsIgnoreCase("image/png")) {
            throw new IllegalArgumentException("Format Image tidak sesuai");
        }

        // Buat folder jika belum ada
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // Simpan file di folder
        String filePath = Paths.get(uploadDir, file.getOriginalFilename()).toString();
        file.transferTo(new File(filePath));

        // Simpan info file ke DB
        Long id = userRepo.saveImage(email, file.getOriginalFilename().toString(), filePath);
        if (id == null) throw new BadRequestException("Gagal menyimpan image");
        return userRepo.findByEmail(email);

    }
}
