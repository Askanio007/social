package com.social.server.entity;

/**
 * Интерфейс для отображения минимальной информации об объекте
 */
public interface ShortModel {

    /**
     * Идентификатор
     */
    long getId();

    /**
     * Полное имя / название
     */
    String getFullName();

    /**
     * Мини-фотография
     */
    Image getMiniImage();

    /**
     * Фотография
     */
    Image getImage();

    /**
     * Задать фотографию
     */
    void setImage(Image image);

    /**
     * Задать мини фотографию
     */
    void setMiniImage(Image image);

}
