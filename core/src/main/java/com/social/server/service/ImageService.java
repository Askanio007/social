package com.social.server.service;

import com.social.server.entity.Image;

/**
 * Сервис картинок
 */
public interface ImageService {

    /**
     * Удалить картинки с сервера и из базы
     * @param images - список картинок
     */
    void deleteImage(Image... images);
}
