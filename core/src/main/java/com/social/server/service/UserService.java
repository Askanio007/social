package com.social.server.service;

import com.social.server.dto.UserDto;
import com.social.server.entity.User;
import com.social.server.http.model.RegistrationModel;
import com.social.server.http.model.RestorePasswordModel;
import com.social.server.http.model.UserDetailsModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Сервис для сущности User
 * @see User
 */
public interface UserService extends CommonService<User, Long> {
    /**
     * Зарегистрировать нового пользователя в системе
     * @param registrationModel - необходимые данные пользователя при регистрации {@link RegistrationModel}
     * @return модель данных пользователя
     */
    UserDto registerUser(RegistrationModel registrationModel);

    /**
     * Проверить существование имейла в системе
     */
    boolean isEmailExist(String email);

    /**
     * Обновить данные пользователя
     * @param userDetailsModel - данные пользователя {@link UserDetailsModel}
     * @return модель данных пользователя
     */
    UserDto updateProfile(UserDetailsModel userDetailsModel);

    /**
     * Поиск пользователя по идентификатору
     * @return модель данных пользователя
     */
    UserDto findBy(long id);

    /**
     * Поиск пользователей по имени
     * @param rootUserId - идентификатор пользователя, который производит поиск
     * @param userName - имя пользователя, по которому осуществляется поиск
     */
    List<UserDto> search(long rootUserId, String userName);

    /**
     * Получить пользователя по email и паролю
     * @return модель данных пользователя
     */
    UserDto findBy(String email, String password);

    /**
     * Сохранить фото пользователя
     * @param rootUserId - идентификатор пользователя, для которого сохраняется фото
     * @param file - файл с фото
     * @param isMini - миниатюра фото или нет
     * @return идентификатор фотографии
     */
    long savePhoto(long rootUserId, MultipartFile file, boolean isMini);

    /**
     * Изменить пароль пользователю
     * @param model - пароль с подтверждением
     */
    void changePassword(RestorePasswordModel model);
}
