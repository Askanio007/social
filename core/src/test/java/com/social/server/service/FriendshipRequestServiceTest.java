package com.social.server.service;

import com.social.server.dao.FriendshipRequestRepository;
import com.social.server.dao.UserRepository;
import com.social.server.service.impl.FriendshipRequestServiceImpl;
import org.junit.Test;
import org.mockito.Mockito;

public class FriendshipRequestServiceTest {

    private final long USER_ID = 1;
    private final long FRIEND_ID = 2;
    private final String FRIEND_NAME = "FRIEND";

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final FriendService friendService = Mockito.mock(FriendService.class);
    private final DialogService dialogService = Mockito.mock(DialogService.class);
    private final FriendshipRequestRepository friendshipRequestRepository = Mockito.mock(FriendshipRequestRepository.class);
    private FriendshipRequestServiceImpl friendshipRequestService = new FriendshipRequestServiceImpl(friendshipRequestRepository, userRepository, friendService, dialogService);



    @Test
    public void successCreateRequest() {
        //friendshipRequestService.create();
    }
}
