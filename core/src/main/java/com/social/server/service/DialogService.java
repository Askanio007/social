package com.social.server.service;

import com.social.server.dto.DialogDto;
import com.social.server.entity.Dialog;
import com.social.server.entity.User;

import java.util.List;
import java.util.Set;

public interface DialogService extends CommonService<Dialog, Long> {
    List<DialogDto> findBy(long rootUserId);
    void create(Set<User> users);
}
