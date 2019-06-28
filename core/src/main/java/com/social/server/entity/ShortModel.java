package com.social.server.entity;

/**
 * Интерфейс для отображения минимальной информации об объекте
 */
public interface ShortModel {

    /**
     * Идентификатор
     */
    Long getId();

    /**
     * Полное имя / название
     */
    String getFullName();

    /**
     * Фотография
     */
    Image getImage();

}
