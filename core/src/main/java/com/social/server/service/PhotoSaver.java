package com.social.server.service;

import com.social.server.entity.ShortModel;
import org.springframework.web.multipart.MultipartFile;

/**
 * Работы с фотографиями
 * @param <T> - модель, у которой предполагается наличие аватара
 */
public interface PhotoSaver<T extends ShortModel> {
    /**
     * Сохранить аватар или его мини-копию
     * @param shortModel - модель для которой сохраняется фото
     * @param file - фото
     * @param isMini - мини или обычная фото
     * @return идентификатор фотографии
     */
    long savePhoto(T shortModel, MultipartFile file, boolean isMini);
}
