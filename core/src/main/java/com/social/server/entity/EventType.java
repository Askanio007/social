package com.social.server.entity;

import lombok.Getter;

@Getter
public enum EventType {
    ENTER_GROUP("event.enter.group.description"),
    ADD_FRIEND("event.add.friend.description"),
    ADD_PUBLIC_MESSAGE("event.add.public.message");

    private String descriptionCode;

    EventType(String descriptionCode) {
        this.descriptionCode = descriptionCode;
    }
}
