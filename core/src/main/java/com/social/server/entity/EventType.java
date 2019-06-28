package com.social.server.entity;

import lombok.Getter;

/**
 * Типы событий
 */
@Getter
public enum EventType {
    /**
     * Пользователь вступил в группу
     */
    ENTER_GROUP("event.enter.group.description"),

    /**
     * Пользователь доавбил друга
     */
    ADD_FRIEND("event.add.friend.description"),

    /**
     * Пользователь написал публичное сообщение
     */
    ADD_PUBLIC_MESSAGE("event.add.public.message");

    private String descriptionCode;

    EventType(String descriptionCode) {
        this.descriptionCode = descriptionCode;
    }
}
