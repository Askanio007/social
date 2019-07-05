package com.social.server.service;

import com.social.server.dao.PublicMessageRepository;
import com.social.server.dao.UserRepository;
import com.social.server.dto.PublicMessageDto;
import com.social.server.entity.EventType;
import com.social.server.entity.PublicMessage;
import com.social.server.entity.PublicMessageRecipientType;
import com.social.server.entity.User;
import com.social.server.http.model.PublicMessageModel;
import com.social.server.service.impl.PublicMessageServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PublicMessageServiceTest {
    private final static long SENDER_ID = 1;
    private final static Long RECIPIENT_ID = 1L;
    private final static long DIALOG_ID = 1;
    private final static String MESSAGE = "TEST";
    private final static String USER_NAME = "MAIN";

    private final PublicMessageRepository publicMessageRepository = Mockito.mock(PublicMessageRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final EventService eventService = Mockito.mock(EventService.class);
    private final PublicMessageService service = new PublicMessageServiceImpl(publicMessageRepository, userRepository, eventService);

    @Before
    public void setUp() {
        User user = new User();
        user.setName(USER_NAME);
        when(userRepository.getOne(SENDER_ID)).thenReturn(user);
        when(publicMessageRepository.save(any(PublicMessage.class))).then(invocation -> invocation.getArgument(0));
    }

    @Test
    public void successCreateMessage() {
        PublicMessageModel message = new PublicMessageModel();
        message.setSenderId(SENDER_ID);
        message.setMessage(MESSAGE);
        message.setRecipientType(PublicMessageRecipientType.GROUP);
        message.setRecipientId(RECIPIENT_ID);

        PublicMessageDto dto = service.create(message);
        verify(eventService).createEvent(eq(SENDER_ID), eq(0L), isNull(), eq(EventType.ADD_PUBLIC_MESSAGE));
        Assert.assertEquals(dto.getMessage(), MESSAGE);
        Assert.assertEquals(dto.getRecipientId(), RECIPIENT_ID);
        Assert.assertEquals(dto.getRecipientType(), PublicMessageRecipientType.GROUP);
    }
}
