package com.social.server.service;

import com.social.server.dao.UserRepository;
import com.social.server.entity.EventType;
import com.social.server.entity.User;
import com.social.server.service.impl.FriendServiceImpl;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FriendServiceTest {

    private final long USER_ID = 1;
    private final long FRIEND_ID = 2;
    private final String FRIEND_NAME = "FRIEND";

    private final EventService eventService = Mockito.mock(EventService.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final DialogService dialogService = Mockito.mock(DialogService.class);
    private FriendServiceImpl friendService = new FriendServiceImpl(userRepository, eventService, dialogService);

    @Test
    public void successAddFriend() {
        setReturnUsers(false);
        friendService.addFriend(USER_ID, FRIEND_ID);
        generalCheck();
        verify(eventService, times(1)).createEvent(eq(USER_ID), eq(0L), eq(FRIEND_NAME), eq(EventType.ADD_FRIEND));
    }

    @Test
    public void successRemoveFriend() {
        setReturnUsers(true);
        friendService.remove(USER_ID, FRIEND_ID);
        generalCheck();
    }

    @Test(expected = EntityNotFoundException.class)
    public void failedAddFriend() {
        friendService.addFriend(0, 0);
    }

    @Test(expected = EntityNotFoundException.class)
    public void failedRemoveFriend() {
        friendService.remove(0, 0);
    }

    private void generalCheck() {
        verify(userRepository, times(1)).findById(USER_ID);
        verify(userRepository, times(1)).findById(FRIEND_ID);
    }

    private void setReturnUsers(boolean withFriends) {
        User user = new User();
        User friend = new User();
        friend.setName(FRIEND_NAME);
        if (withFriends) {
            user.getFriends().add(friend);
            friend.getFriends().add(user);
        }
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(FRIEND_ID)).thenReturn(Optional.of(friend));
    }
}
