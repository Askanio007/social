package com.social.server.service.impl;

import com.social.server.dao.ImageRepository;
import com.social.server.entity.Image;
import com.social.server.service.ImageService;
import com.social.server.util.FileUtil;
import com.social.server.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ImageServiceImpl extends CommonServiceImpl<Image, Long, ImageRepository> implements ImageService {

    public ImageServiceImpl(ImageRepository repository) {
        super(repository);
    }

    @Override
    public void deleteImage(Image... images) {
        for (Image image : images) {
            if (image != null) {
                log.debug("Delete image along the path: {}", image.getPath());
                FileUtil.deleteFile(image.getPath());
                log.debug("Delete image from db; id={}", image.getId());
                repository.delete(image);
                log.debug("Delete image completed successfully");
            }
        }
    }

    @Override
    public byte[] getImageAsByteArray(long imageId) {
        if (imageId == 0) {
            return ImageUtil.getDefaultImageAsStream();
        }
        Image image = getById(imageId);
        return ImageUtil.getImageAsStream(image.getPath());
    }

    @Override
    public String getImageAsBase64Encode(long imageId) {
        return ImageUtil.convertImageTo64encode(imageId == 0 ? null : getById(imageId));
    }
}
