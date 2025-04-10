package com.example.mobilear.service;

import com.example.mobilear.firebase.FirebaseInitialization;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class FileUploadService {
    private final FirebaseInitialization firebaseInitialization;

    public FileUploadService(FirebaseInitialization firebaseInitialization, Firestore firestore) {
        this.firebaseInitialization = firebaseInitialization;
    }

    public String uploadFile(MultipartFile file, String fileType) {
        try {
            Bucket bucket = firebaseInitialization.getStorageBucket();

            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null) {
                throw new IllegalArgumentException("File name cannot be null");
            }

            // Chọn thư mục dựa trên loại file
            String folder = switch (fileType.toLowerCase()) {
                case "thumbnail" -> "thumbnails";
                case "model" -> "models";
                default -> throw new IllegalArgumentException("Invalid file type: " + fileType);
            };

            String fileName = UUID.randomUUID() + "_" + originalFileName;
            String fullPath = folder + "/" + fileName;

            Blob blob = bucket.create(fullPath, file.getInputStream(), file.getContentType());

            // Tạo URL công khai
            String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/" + bucket.getName() + "/o/"
                    + blob.getName().replaceAll("/", "%2F") + "?alt=media";

            return downloadUrl;

        } catch (IOException e) {
            e.printStackTrace();
            return "Upload failed: " + e.getMessage();
        }
    }

}
