package com.springboot.stability.controller;

import com.springboot.stability.service.ImageGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/stable")
@RequiredArgsConstructor
public class ImageGenerationController {

    private final ImageGenerationService imageGenerationService;

    @PostMapping("/image2d")
    public ResponseEntity<Map<String, String>> generate2DImage(
            @RequestParam("image") MultipartFile image
    ) throws IOException {
        String imageUrl = imageGenerationService.generateImage(image);
        return ResponseEntity.ok(Map.of("url", imageUrl));
    }
}