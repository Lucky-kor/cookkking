package com.springboot.stability.uploader;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LocalFileUploader implements ImageUploader {

    private static final String BASE_DIR = "src/main/resources/static/generated";
    private static final String BASE_URL = "http://localhost:8080/generated";

    @Override
    public String uploadImage(byte[] imageBytes, String fileName) {
        try {
            Path dirPath = Paths.get(BASE_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            Path filePath = dirPath.resolve(fileName);
            Files.write(filePath, imageBytes);

            return BASE_URL + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("로컬 파일 저장 중 오류 발생", e);
        }
    }
}
