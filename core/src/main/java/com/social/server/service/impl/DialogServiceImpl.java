package com.social.server.service.impl;

import com.social.server.dao.DialogRepository;
import com.social.server.dto.DialogDto;
import com.social.server.entity.Dialog;
import com.social.server.entity.User;
import com.social.server.service.DialogService;
import com.social.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DialogServiceImpl extends CommonServiceImpl<Dialog, Long, DialogRepository> implements DialogService {

    private final UserService userService;

    @Autowired
    public DialogServiceImpl(DialogRepository repository, UserService userService) {
        super(repository);
        this.userService = userService;
    }

    @Override
    public List<DialogDto> findBy(long rootUserId) {
        return DialogDto.of(repository.findByUsersIdInOrderByDateLastMessageDesc(rootUserId));
    }

    @Override
    public DialogDto create(List<User> users) {
        return create(new HashSet<>(users));
    }

    @Override
    public DialogDto getDialogBy(List<Long> usersId) {
        Set<Long> userList = new HashSet<>(usersId);
        DialogDto dialogDto = DialogDto.of(repository.findOneByUsersId(userList));
        if (dialogDto == null) {
            return create(userList.stream().map(userService::getById).collect(Collectors.toList()));
        }
        return dialogDto;
    }

    private DialogDto create(Set<User> users) {
        if (dialogExist(users)) {
            return getDialogBy(users.stream().map(User::getId).collect(Collectors.toList()));
        }

        Dialog dialog = new Dialog();
        dialog.setUsers(users);
        return DialogDto.of(repository.save(dialog));
    }

    private boolean dialogExist(Set<User> users) {
        return repository.existsByUsersIdIn(users.stream()
                .map(User::getId)
                .collect(Collectors.toSet()));
    }
}
