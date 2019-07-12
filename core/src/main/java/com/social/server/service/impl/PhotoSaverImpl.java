package com.social.server.service.impl;

import com.social.server.entity.Image;
import com.social.server.entity.ShortModel;
import com.social.server.service.ImageService;
import com.social.server.service.PhotoSaver;
import com.social.server.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service("photoSaver")
public class PhotoSaverImpl<T extends ShortModel> implements PhotoSaver<T> {

    private final ImageService imageService;

    @Autowired
    public PhotoSaverImpl(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public String savePhoto(T shortModel, MultipartFile file, boolean isMini) {
        if (!isMini) {
            imageService.deleteImage(shortModel.getMiniImage(), shortModel.getImage());
        }

        Image image = ImageUtil.saveImage(file, shortModel.getId(), isMini);

        if (image == null) {
            log.warn("Save image return null; modelId={} file={}", shortModel.getId(), file);
            return null;
        }

        if (isMini) {
            shortModel.setMiniImage(image);
        } else {
            shortModel.setImage(image);
        }

        return ImageUtil.convertImageTo64encode(isMini ? shortModel.getMiniImage() : shortModel.getImage());
    }
}
