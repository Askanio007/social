package com.social.server.service;

import com.social.server.entity.Image;

/**
 * Сервис картинок
 */
public interface ImageService extends CommonService<Image, Long> {

    /**
     * Удалить картинки с сервера и из базы
     * @param images - список картинок
     */
    void deleteImage(Image... images);

    /**
     * Получить картинку по байтам
     */
    byte[] getImageAsByteArray(long imageId);

    /**
     * Получить картинку в кодировке base64
     */
    String getImageAsBase64Encode(long imageId);

}
