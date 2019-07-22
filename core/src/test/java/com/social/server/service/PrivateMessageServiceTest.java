package com.social.server.service;

import com.social.server.dao.PrivateMessageRepository;
import com.social.server.dto.PhotoAndNameDto;
import com.social.server.dto.PrivateMessageDto;
import com.social.server.entity.Dialog;
import com.social.server.entity.PrivateMessage;
import com.social.server.entity.User;
import com.social.server.service.impl.PrivateMessageServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PrivateMessageServiceTest {
    private final static long SENDER_ID = 1;
    private final static long DIALOG_ID = 1;
    private final static String MESSAGE = "TEST";
    private final static String USER_NAME = "MAIN";

    private final UserService userService = Mockito.mock(UserService.class);
    private final DialogService dialogService = Mockito.mock(DialogService.class);
    private final PrivateMessageRepository repository = Mockito.mock(PrivateMessageRepository.class);
    private final PrivateMessageService privateMessageService = new PrivateMessageServiceImpl(repository, userService, dialogService);

    @Before
    public void setUp() {
        User user = new User();
        user.setName(USER_NAME);
        Dialog dialog = new Dialog();

        when(dialogService.getById(DIALOG_ID)).thenReturn(dialog);
        when(userService.getById(SENDER_ID)).thenReturn(user);
        when(repository.save(any(PrivateMessage.class))).then(invocation -> invocation.getArgument(0));
    }

    @Test
    public void successSave() {
        PrivateMessageDto message = new PrivateMessageDto();
        PhotoAndNameDto photoAndNameDto = new PhotoAndNameDto();
        photoAndNameDto.setId(SENDER_ID);
        message.setSender(photoAndNameDto);
        message.setDialogId(DIALOG_ID);
        message.setMessage(MESSAGE);

        PrivateMessageDto dto = privateMessageService.save(message);
        ArgumentCaptor<PrivateMessage> privateMessageCaptor = ArgumentCaptor.forClass(PrivateMessage.class);

        verify(repository).save(privateMessageCaptor.capture());
        Assert.assertEquals(privateMessageCaptor.getValue().getDialog().getLastMessage(), MESSAGE);
        Assert.assertEquals(dto.getMessage(), MESSAGE);
        Assert.assertEquals(dto.getSender().getFullName(), USER_NAME);

    }
}
