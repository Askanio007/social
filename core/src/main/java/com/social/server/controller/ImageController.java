package com.social.server.controller;

import com.social.server.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images/{imageId}")
    public byte[] getImageByteArray(@PathVariable long imageId) {
        return imageService.getImageAsByteArray(imageId);
    }

    @GetMapping("/api/v1/images/{imageId}/base64")
    public String getImageBase64Encode(@PathVariable long imageId) {
        return imageService.getImageAsBase64Encode(imageId);
    }
}
