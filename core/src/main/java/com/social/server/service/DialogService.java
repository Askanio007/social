package com.social.server.service;

import com.social.server.dto.DialogDto;
import com.social.server.entity.Dialog;
import com.social.server.entity.User;
import org.springframework.data.domain.Page;

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
    Page<DialogDto> findBy(long rootUserId, int page);

    /**
     * Создать диалог между пользователями
     */
    DialogDto create(List<User> users);

    /**
     * Получить диалог пользователей. Если такого нет, то создать
     */
    DialogDto getDialogBy(List<Long> usersId);
}
