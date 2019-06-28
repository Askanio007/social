package com.social.server.service;

/**
 * Общий сервис
 * @param <Class> - Entity-класс
 * @param <ID> - Тип идентификатора Entity
 */
public interface CommonService<Class, ID> {

    /**
     * Найти Entity по идентификатору
     * @param id - идентификатор Entity
     */
    Class findById(ID id);
}
