package com.social.server.service;

import com.social.server.dto.UserDto;

public interface RestorePasswordService {
    /**
     * Отправить сообщение со ссылкой на восстановление пароля
     * @param email - адрес отправки
     * @return успешно или нет
     */
    boolean sendRestoreLinkTo(String email);

    /**
     * Проверить существование и валидность токена
     * @return пользователь, который запрашивал токен
     */
    UserDto checkRestorePasswordToken(String token);
}
