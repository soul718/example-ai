package com.ai.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.io.IOException;

@Service
public class VisionService {
    @Value("${customvision.endpoint}")
    private String endpoint;

    @Value("${customvision.projectId}")
    private String projectId;

    @Value("${customvision.iterationName}")
    private String iteration;

    @Value("${customvision.key}")
    private String predictionKey;

    public String analyzeImage(org.springframework.web.multipart.MultipartFile file) {
        String url = String.format("%s/customvision/v3.0/Prediction/%s/classify/iterations/%s/image",
                endpoint, projectId, iteration);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Prediction-Key", predictionKey);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        try {
            HttpEntity<byte[]> request = new HttpEntity<>(file.getBytes(), headers);
            RestTemplate client = new RestTemplate();
            ResponseEntity<String> response = client.exchange(url, HttpMethod.POST, request, String.class);
            return response.getBody();
        } catch (IOException e) {
            return "{\"error\":\"Error procesando la imagen\"}";
        }
    }

}
