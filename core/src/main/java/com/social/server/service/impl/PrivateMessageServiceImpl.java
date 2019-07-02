package com.social.server.service.impl;

import com.social.server.dao.PrivateMessageRepository;
import com.social.server.dto.PrivateMessageDto;
import com.social.server.entity.Dialog;
import com.social.server.entity.PrivateMessage;
import com.social.server.service.DialogService;
import com.social.server.service.PrivateMessageService;
import com.social.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PrivateMessageServiceImpl extends CommonServiceImpl<PrivateMessage, Long, PrivateMessageRepository> implements PrivateMessageService {

    private final UserService userService;
    private final DialogService dialogService;

    @Autowired
    public PrivateMessageServiceImpl(PrivateMessageRepository repository, UserService userService, DialogService dialogService) {
        super(repository);
        this.userService = userService;
        this.dialogService = dialogService;
    }

    @Override
    public PrivateMessageDto save(PrivateMessageDto message) {
        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setSender(userService.getById(message.getSenderId()));
        privateMessage.setMessage(message.getMessage());

        Dialog dialog = dialogService.getById(message.getDialogId());
        dialog.setDateLastMessage(privateMessage.getCreateDate());
        dialog.setLastMessage(privateMessage.getMessage());
        privateMessage.setDialog(dialog);

        return PrivateMessageDto.of(repository.save(privateMessage));
    }

    @Override
    public List<PrivateMessageDto> findLastBy(long dialogId) {
        List<PrivateMessageDto> list = PrivateMessageDto.of(repository.findByDialogIdOrderByCreateDateDesc(dialogId, PageRequest.of(0, 10)));
        Collections.reverse(list);
        return list;
    }
}
