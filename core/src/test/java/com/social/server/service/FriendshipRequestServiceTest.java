package com.social.server.service;

import com.social.server.dao.FriendshipRequestRepository;
import com.social.server.dao.UserRepository;
import com.social.server.entity.FriendshipRequest;
import com.social.server.entity.User;
import com.social.server.entity.UserRelation;
import com.social.server.http.model.FriendshipRequestModel;
import com.social.server.service.impl.FriendshipRequestServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class FriendshipRequestServiceTest {

    private final long USER_ID = 1;
    private final long FRIEND_ID = 2;
    private final String FRIEND_NAME = "FRIEND";
    private final String USER_NAME = "MAIN";
    private final long REQUEST_ID = 1;
    private User user;
    private User friend;

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final FriendService friendService = Mockito.mock(FriendService.class);
    private final FriendshipRequestRepository friendshipRequestRepository = Mockito.mock(FriendshipRequestRepository.class);
    private FriendshipRequestServiceImpl friendshipRequestService = new FriendshipRequestServiceImpl(friendshipRequestRepository, userRepository, friendService);


    @Before
    public void setUp() {
        user = new User();
        user.setName(USER_NAME);
        friend = new User();
        friend.setName(FRIEND_NAME);

        FriendshipRequest friendshipRequest = new FriendshipRequest();
        friendshipRequest.setAccept(false);
        friendshipRequest.setRequestFrom(user);
        friendshipRequest.setRequestTo(friend);

        when(friendshipRequestRepository.findById(REQUEST_ID)).thenReturn(Optional.of(friendshipRequest));
        when(userRepository.getOne(USER_ID)).thenReturn(user);
        when(userRepository.getOne(FRIEND_ID)).thenReturn(friend);
    }

    @Test
    public void successCreateRequest() {
        FriendshipRequestModel model = new FriendshipRequestModel();
        model.setFromUserId(1);
        model.setToUserId(2);

        friendshipRequestService.create(model);
        ArgumentCaptor<FriendshipRequest> request = ArgumentCaptor.forClass(FriendshipRequest.class);
        verify(friendshipRequestRepository).save(request.capture());
        verify(userRepository).getOne(USER_ID);
        verify(userRepository).getOne(FRIEND_ID);
        FriendshipRequest request1 = request.getValue();
        Assert.assertEquals(request1.getRequestFrom().getName(), USER_NAME);
        Assert.assertEquals(request1.getRequestTo().getName(), FRIEND_NAME);
    }

    @Test
    public void successAcceptRequest() {
        friendshipRequestService.accept(REQUEST_ID);
        verify(friendService).addFriend(eq(0L), eq(0L));
    }

    @Test
    public void checkUserRelationMe() {
        UserRelation relation = friendshipRequestService.getRelation(USER_ID, USER_ID);
        Assert.assertEquals(relation, UserRelation.ME);
    }

    @Test
    public void checkUserRelationFriend() {
        when(friendService.isFriends(USER_ID, FRIEND_ID)).thenReturn(true);
        UserRelation relation = friendshipRequestService.getRelation(USER_ID, FRIEND_ID);
        Assert.assertEquals(relation, UserRelation.FRIEND);
    }

    @Test
    public void checkUserRelationNotFriend() {
        when(friendService.isFriends(USER_ID, FRIEND_ID)).thenReturn(false);
        when(friendshipRequestRepository.existsByRequestFromIdAndRequestToIdAndAcceptIsFalse(USER_ID, FRIEND_ID)).thenReturn(false);
        UserRelation relation = friendshipRequestService.getRelation(USER_ID, FRIEND_ID);
        Assert.assertEquals(relation, UserRelation.NOT_FRIEND);
    }

    @Test
    public void checkUserRelationRequestFriend() {
        when(friendService.isFriends(USER_ID, FRIEND_ID)).thenReturn(false);
        when(friendshipRequestRepository.existsByRequestFromIdAndRequestToIdAndAcceptIsFalse(USER_ID, FRIEND_ID)).thenReturn(true);
        UserRelation relation = friendshipRequestService.getRelation(USER_ID, FRIEND_ID);
        Assert.assertEquals(relation, UserRelation.REQUEST_FRIEND);
    }
}
