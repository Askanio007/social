package com.social.server.service;

/**
 * Общий сервис
 * @param <Class> - Entity-класс
 * @param <ID> - Тип идентификатора Entity
 */
public interface CommonService<Class, ID> {

    /**
     * Получить Entity по идентификатору
     * @param id - идентификатор Entity
     */
    Class getById(ID id);

    /**
     * Сохранить объект
     */
    Class save(Class entity);
}
