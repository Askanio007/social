package com.social.server.service.impl;

import com.social.server.dao.DialogRepository;
import com.social.server.dto.DialogDto;
import com.social.server.entity.Dialog;
import com.social.server.entity.User;
import com.social.server.service.DialogService;
import com.social.server.service.UserService;
import com.social.server.service.transactional.ReadTransactional;
import com.social.server.service.transactional.WriteTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DialogServiceImpl extends CommonServiceImpl<Dialog, Long, DialogRepository> implements DialogService {

    private final UserService userService;

    @Autowired
    public DialogServiceImpl(DialogRepository repository, UserService userService) {
        super(repository);
        this.userService = userService;
    }

    @Override
    public Page<DialogDto> findBy(long rootUserId, int page) {
        return repository.findByUsersIdInOrderByDateLastMessageDesc(rootUserId, PageRequest.of(page, 10)).map(DialogDto::of);
    }

    @Override
    @WriteTransactional
    public DialogDto create(List<User> users) {
        return create(new HashSet<>(users));
    }

    @Override
    @ReadTransactional
    public DialogDto getDialogBy(List<Long> usersId) {
        Set<Long> userList = new HashSet<>(usersId);
        DialogDto dialogDto = DialogDto.of(repository.findOneByUsersId(userList));
        if (dialogDto == null) {
            log.debug("Dialog not found. Create dialog between users: {}", usersId);
            return create(userList.stream().map(userService::getById).collect(Collectors.toList()));
        }
        return dialogDto;
    }

    @Override
    public long countUnreadMessage(long userId) {
        return repository.countNotReadMessages(userId);
    }

    private DialogDto create(Set<User> users) {
        log.debug("Check existing dialog between {}...", users);
        if (dialogExist(users)) {
            log.debug("Dialog exist. Return it");
            return getDialogBy(users.stream().map(User::getId).collect(Collectors.toList()));
        }

        log.debug("Dialog not exist. Create it");
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
