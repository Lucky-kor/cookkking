package com.springboot.stability.uploader;

public interface ImageUploader {
    String uploadImage(byte[] imageBytes, String fileName);
}
