package com.ai.demo.controller;

import com.ai.demo.service.VisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/vision")
public class VisionController {
    @Autowired
    private VisionService visionService;

    @PostMapping("/analyze")
    public ResponseEntity<?> analyze(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(visionService.analyzeImage(file));
    }
}
