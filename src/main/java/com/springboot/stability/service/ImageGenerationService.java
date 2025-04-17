package com.springboot.stability.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.springboot.stability.uploader.ImageUploader;
import com.springboot.stability.MultipartInputStreamFileResource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageGenerationService {

    private final ImageUploader imageUploader;

    @Value("${stability.api-key}")
    private String apiKey;

    @Value("${stability.engine-id}")
    private String engineId;

    @Value("${stability.base-url}")
    private String baseUrl;

    private static final String PROMPT = "2D anime style illustration";

    public String generateImage(MultipartFile inputImage) throws IOException {
        String apiUrl = baseUrl + "/v2beta/stable-image/generate/sd3"; // or /v1alpha/... ← 정확한 엔드포인트는 네가 쓰는 것에 맞춰

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(apiKey);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON)); // JSON 응답을 원한다면

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new MultipartInputStreamFileResource(inputImage.getInputStream(), inputImage.getOriginalFilename()));
        body.add("prompt", "2D anime style illustration");
        body.add("strength", "0.7");
        body.add("mode", "image-to-image");
        // 필요시 body.add("model", "sd3.5-large-turbo");
        // 필요시 body.add("cfg_scale", "4");
        // 필요시 body.add("output_format", "png");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JsonNode> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, JsonNode.class);
        String base64 = response.getBody().get("image").asText(); // field 이름이 실제 응답 구조에 따라 다를 수 있음
        byte[] imageBytes = Base64.getDecoder().decode(base64);

        String fileName = UUID.randomUUID() + ".png";
        return imageUploader.uploadImage(imageBytes, fileName);
    }

}
