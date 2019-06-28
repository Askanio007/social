package com.social.server.service.impl;

import com.social.server.dao.DialogRepository;
import com.social.server.dto.DialogDto;
import com.social.server.entity.Dialog;
import com.social.server.entity.User;
import com.social.server.service.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DialogServiceImpl extends CommonServiceImpl<Dialog, Long, DialogRepository> implements DialogService {

    @Autowired
    public DialogServiceImpl(DialogRepository repository) {
        super(repository);
    }

    @Override
    public List<DialogDto> findBy(long rootUserId) {
        return DialogDto.of(repository.findByUsersIdInOrderByDateLastMessageDesc(rootUserId));
    }

    private void create(Set<User> users) {
        Dialog dialog = new Dialog();
        dialog.setUsers(users);
        repository.save(dialog);
    }

    @Override
    public void create(User... user) {
        create(new HashSet<>(Arrays.asList(user)));
    }

    @Override
    public void create(List<User> users) {
        create(new HashSet<>(users));
    }
}
