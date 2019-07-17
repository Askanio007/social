package com.social.server.service.impl;

import com.social.server.dao.FriendshipRequestRepository;
import com.social.server.dao.UserRepository;
import com.social.server.dto.FriendshipRequestDto;
import com.social.server.entity.FriendshipRequest;
import com.social.server.entity.UserRelation;
import com.social.server.exception.FriendshipRequestValidationException;
import com.social.server.http.model.FriendshipRequestModel;
import com.social.server.service.FriendService;
import com.social.server.service.FriendshipRequestService;
import com.social.server.service.transactional.ReadTransactional;
import com.social.server.service.transactional.WriteTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class FriendshipRequestServiceImpl extends CommonServiceImpl<FriendshipRequest, Long, FriendshipRequestRepository> implements FriendshipRequestService {

    private final UserRepository userRepository;
    private final FriendService friendService;

    @Autowired
    public FriendshipRequestServiceImpl(FriendshipRequestRepository friendshipRequestRepository,
                                        UserRepository userRepository,
                                        FriendService friendService) {
        super(friendshipRequestRepository);
        this.userRepository = userRepository;
        this.friendService = friendService;
    }

    @Override
    @WriteTransactional
    public void create(FriendshipRequestModel model) {
        if (model == null) {
            throw new FriendshipRequestValidationException("common.validation.model.incorrect", Collections.singletonList("common.validation.model.null"));
        }
        validateEmptyEntityId(model.getFromUserId());
        validateEmptyEntityId(model.getToUserId());
        FriendshipRequest request = new FriendshipRequest();
        request.setRequestTo(userRepository.getOne(model.getToUserId()));
        request.setRequestFrom(userRepository.getOne(model.getFromUserId()));
        repository.save(request);
    }

    @Override
    @WriteTransactional
    public void accept(long friendshipRequestId) {
        log.debug("Accepting friend request started");
        validateEmptyEntityId(friendshipRequestId);
        FriendshipRequest friendshipRequest = getById(friendshipRequestId);
        friendshipRequest.setAccept(true);
        log.debug("Save friend request");
        friendService.addFriend(friendshipRequest.getRequestTo().getId(), friendshipRequest.getRequestFrom().getId());
        log.debug("Accepting friend request completed successfully");
    }

    @Override
    public void decline(long friendshipRequestId) {
        validateEmptyEntityId(friendshipRequestId);
        repository.deleteById(friendshipRequestId);
    }

    @Override
    public Page<FriendshipRequestDto> find(long userId, int page) {
        validateEmptyEntityId(userId);
        return repository.findAllByAcceptIsFalseAndRequestToId(userId, PageRequest.of(page, 10)).map(FriendshipRequestDto::of);
    }

    @Override
    public long countRequests(long userId) {
        validateEmptyEntityId(userId);
        return repository.countByRequestToIdAndAcceptIsFalse(userId);
    }

    @Override
    public boolean isFriendRequest(long rootUserId, long userId) {
        validateEmptyEntityId(rootUserId, userId);
        return repository.existsByRequestFromIdAndRequestToIdAndAcceptIsFalse(rootUserId, userId);
    }

    @Override
    @ReadTransactional
    public UserRelation getRelation(long rootUserId, long userId) {
        validateEmptyEntityId(rootUserId, userId);
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
