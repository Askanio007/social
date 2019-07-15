package com.social.server.service;

import com.social.server.dto.UserDto;

public interface RestorePasswordService {
    /**
     * Отправить сообщение со ссылкой на восстановление пароля
     * @param email - адрес отправки
     * @param mirror - адрес зеркала, с которого пришёл запроса на восстановление
     */
    void sendRestoreLinkTo(String email, String mirror);

    /**
     * Проверить существование и валидность токена
     * @return пользователь, который запрашивал токен
     */
    UserDto checkRestorePasswordToken(String token);
}
