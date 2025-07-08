package com.example.ticket.service.impl;

import com.example.ticket.configs.MinioConfig;
import com.example.ticket.service.IImageService;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService implements IImageService {
    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            // Validate file
            validateFile(file);

            // Tạo bucket nếu chưa tồn tại
            createBucketIfNotExists();

            // Tạo tên file unique
            String fileName = generateFileName(file.getOriginalFilename());

            // Upload file
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build()
            );

            log.info("Successfully uploaded file: {} to MinIO", fileName);

            // Trả về URL của file
            return getImageUrl(fileName);

        } catch (Exception e) {
            log.error("Error uploading file to MinIO: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to upload image: " + e.getMessage());
        }
    }

    @Override
    public void deleteImage(String fileName) {
        try {
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(fileName)
                    .build()
            );
            log.info("Successfully deleted file: {} from MinIO", fileName);
        } catch (Exception e) {
            log.error("Error deleting file from MinIO: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete image: " + e.getMessage());
        }
    }

    @Override
    public String getImageUrl(String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(minioConfig.getBucketName())
                    .object(fileName)
                    .expiry(60 * 60 * 24 * 7) // 7 days
                    .build()
            );
        } catch (Exception e) {
            log.error("Error getting presigned URL for file: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get image URL: " + e.getMessage());
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Kiểm tra kích thước file (5MB)
        if (file.getSize() > 5242880) {
            throw new IllegalArgumentException("File size exceeds 5MB limit");
        }

        // Kiểm tra loại file
        String contentType = file.getContentType();
        if (contentType == null || !isValidImageType(contentType)) {
            throw new IllegalArgumentException("Invalid file type. Only JPG, JPEG, PNG, GIF, WEBP are allowed");
        }
    }

    private boolean isValidImageType(String contentType) {
        return contentType.equals("image/jpeg") ||
               contentType.equals("image/jpg") ||
               contentType.equals("image/png") ||
               contentType.equals("image/gif") ||
               contentType.equals("image/webp");
    }

    private String generateFileName(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return "movie-images/" + UUID.randomUUID().toString() + extension;
    }

    private void createBucketIfNotExists() {
        try {
            boolean exists = minioClient.bucketExists(
                BucketExistsArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .build()
            );

            if (!exists) {
                minioClient.makeBucket(
                    MakeBucketArgs.builder()
                        .bucket(minioConfig.getBucketName())
                        .build()
                );
                log.info("Created bucket: {}", minioConfig.getBucketName());
            }
        } catch (Exception e) {
            log.error("Error creating bucket: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create bucket: " + e.getMessage());
        }
    }
}
