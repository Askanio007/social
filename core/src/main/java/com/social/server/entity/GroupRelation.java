package com.social.server.entity;

public enum GroupRelation {
    /**
     * Пользователь является администратором группы
     */
    ADMIN,

    /**
     * Пользователь является участником
     */
    PARTICIPANT,

    /**
     * Пользователь не является участником или админом
     */
    NOT_PARTICIPANT
}
