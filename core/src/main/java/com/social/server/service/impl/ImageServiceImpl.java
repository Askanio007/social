package com.social.server.service.impl;

import com.social.server.dao.ImageRepository;
import com.social.server.entity.Image;
import com.social.server.service.ImageService;
import com.social.server.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void deleteImage(Image... images) {
        for (Image image : images) {
            if (image != null) {
                log.debug("Delete image along the path: {}", image.getPath());
                FileUtil.deleteFile(image.getPath());
                log.debug("Delete image from db; id={}", image.getId());
                imageRepository.delete(image);
                log.debug("Delete image completed successfully");
            }
        }
    }
}
