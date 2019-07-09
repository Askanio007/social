package com.social.server.service;

import com.social.server.dto.GroupDto;
import com.social.server.entity.Group;
import com.social.server.entity.GroupRelation;
import com.social.server.http.model.GroupModel;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Сервис для групп
 * @see Group
 */
public interface GroupService extends CommonService<Group, Long> {
    /**
     * Найти группы, в которых состоит пользователь
     * @param userId - идентификатор пользователя
     */
    Page<GroupDto> findBy(long userId, int page);

    /**
     * Найти группу по идентификатору
     */
    GroupDto find(long groupId);

    /**
     * Найти количество участников группы по её идентификатору
     */
    long countParticipant(long groupId);

    /**
     * Создать новую группу
     * @param adminId - идентификатор пользователя, который создаёт группу
     * @param groupModel - модель группы {@link GroupModel}
     */
    GroupDto create(long adminId, GroupModel groupModel);

    /**
     * Проверить, состоит ли пользователь в группе
     * @param userId - идентификатор пользователя
     * @param groupId - идентификатор группы
     */
    boolean isUserHasGroup(long userId, long groupId);

    /**
     * Сделать пользователя участником группы
     * @param userId - идентификатор пользователя
     * @param groupId - идентификатор группы
     */
    void join(long userId, long groupId);

    /**
     * Найти группы по названию
     * @param name - название группы
     */
    List<GroupDto> search(String name);

    /**
     * Сохранить фотографию группы
     * @param groupId - идентификатор группы
     * @param file - файл фотографии
     * @param isMini - большая или маленькая фотография
     */
    String savePhoto(long groupId, MultipartFile file, boolean isMini);

    /**
     * Удалить пользователя из участников группы
     * @param userId - идентификатор пользователя
     * @param groupId - идентификатор группы
     */
    void exit(long userId, long groupId);

    /**
     * Получить количество групп, в которых состоит пользователь
     * @param userId - идентификатор пользователя
     */
    long countBy(long userId);

    /**
     * Получить отношение группы и пользователя {@link GroupRelation}
     * @param groupId - идентификатор группы
     * @param rootUserId - идентификатор пользователь
     * @return отношение группы и пользователя
     */
    GroupRelation getGroupRelationToUser(long groupId, long rootUserId);

    /**
     * Редактировать группу
     * @param groupModel - модель данных группы
     * @return обновлённая группа
     */
    GroupDto edit(GroupModel groupModel);
}
