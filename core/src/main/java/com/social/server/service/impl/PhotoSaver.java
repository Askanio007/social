package com.social.server.service.impl;

import com.social.server.entity.Image;
import com.social.server.entity.ShortModel;
import com.social.server.service.ImageService;
import com.social.server.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class PhotoSaver<T extends ShortModel, ID> {

    private final ImageService imageService;
    private final JpaRepository<T, ID> repository;

    public PhotoSaver(JpaRepository<T, ID> repository,
                      ImageService imageService) {
        this.repository = repository;
        this.imageService = imageService;
    }

    public String savePhoto(ID id, MultipartFile file, boolean isMini) {
        T shortModel = repository.getOne(id);

        if (!isMini) {
            imageService.deleteImage(shortModel.getMiniImage(), shortModel.getImage());
        }

        Image image = ImageUtil.saveImage(file, (long) id, isMini);

        if (image == null) {
            log.warn("Save image return null; modelId={} file={}", id, file);
            return null;
        }

        if (isMini) {
            shortModel.setMiniImage(image);
        } else {
            shortModel.setImage(image);
        }

        shortModel = repository.save(shortModel);
        return ImageUtil.convertImageTo64encode(isMini ? shortModel.getMiniImage() : shortModel.getImage());
    }
}
