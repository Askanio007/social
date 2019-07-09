package com.social.server.service.impl;

import com.social.server.dao.FriendshipRequestRepository;
import com.social.server.dao.UserRepository;
import com.social.server.dto.FriendshipRequestDto;
import com.social.server.entity.FriendshipRequest;
import com.social.server.entity.UserRelation;
import com.social.server.http.model.FriendshipRequestModel;
import com.social.server.service.DialogService;
import com.social.server.service.FriendService;
import com.social.server.service.FriendshipRequestService;
import com.social.server.service.transactional.WriteTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class FriendshipRequestServiceImpl extends CommonServiceImpl<FriendshipRequest, Long, FriendshipRequestRepository> implements FriendshipRequestService {

    private final UserRepository userRepository;
    private final FriendService friendService;
    private final DialogService dialogService;

    @Autowired
    public FriendshipRequestServiceImpl(FriendshipRequestRepository friendshipRequestRepository,
                                        UserRepository userRepository,
                                        FriendService friendService,
                                        DialogService dialogService) {
        super(friendshipRequestRepository);
        this.userRepository = userRepository;
        this.friendService = friendService;
        this.dialogService = dialogService;
    }

    @Override
    public void create(FriendshipRequestModel model) {
        FriendshipRequest request = new FriendshipRequest();
        request.setRequestTo(userRepository.getOne(model.getToUserId()));
        request.setRequestFrom(userRepository.getOne(model.getFromUserId()));
        repository.save(request);
    }

    @Override
    @WriteTransactional
    public void accept(long friendshipRequestId) {
        FriendshipRequest friendshipRequest = getById(friendshipRequestId);
        friendshipRequest.setAccept(true);
        repository.save(friendshipRequest);
        friendService.addFriend(friendshipRequest.getRequestTo().getId(), friendshipRequest.getRequestFrom().getId());
        dialogService.create(Arrays.asList(friendshipRequest.getRequestFrom(), friendshipRequest.getRequestTo()));
    }

    @Override
    public void decline(long friendshipRequestId) {
        repository.deleteById(friendshipRequestId);
    }

    @Override
    public Page<FriendshipRequestDto> find(long userId, int page) {
        return repository.findAllByAcceptIsFalseAndRequestToId(userId, PageRequest.of(page, 10)).map(FriendshipRequestDto::of);
    }

    @Override
    public long countRequests(long userId) {
        return repository.countByRequestToIdAndAcceptIsFalse(userId);
    }

    @Override
    public boolean isFriendRequest(long rootUserId, long userId) {
        return repository.existsByRequestFromIdAndRequestToIdAndAcceptIsFalse(rootUserId, userId);
    }

    @Override
    public UserRelation getRelation(long rootUserId, long userId) {
        if (rootUserId == userId) {
            return UserRelation.ME;
        }
        if (friendService.isFriends(rootUserId, userId)) {
            return UserRelation.FRIEND;
        }
        if (isFriendRequest(rootUserId, userId)) {
            return UserRelation.REQUEST_FRIEND;
        }
        return UserRelation.NOT_FRIEND;
    }
}
