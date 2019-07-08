package com.social.server.service;

import com.social.server.dto.DialogDto;
import com.social.server.entity.Dialog;
import com.social.server.entity.User;

import java.util.List;

/**
 * Сервис для диалогов
 * @see Dialog
 */
public interface DialogService extends CommonService<Dialog, Long> {
    /**
     * Получить все диалоги пользователя
     * @param rootUserId - идентификатор пользователя
     */
    List<DialogDto> findBy(long rootUserId);

    /**
     * Создать диалог между пользователями
     */
    DialogDto create(List<User> users);

    /**
     * Получить диалог пользователей. Если такого нет, то создать
     */
    DialogDto getDialogBy(List<Long> usersId);

}
