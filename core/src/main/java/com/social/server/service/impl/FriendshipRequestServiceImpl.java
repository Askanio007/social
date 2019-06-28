package com.social.server.service.impl;

import com.social.server.dao.FriendshipRequestRepository;
import com.social.server.dao.UserRepository;
import com.social.server.dto.FriendshipRequestDto;
import com.social.server.entity.FriendshipRequest;
import com.social.server.entity.UserRelation;
import com.social.server.service.DialogService;
import com.social.server.service.FriendService;
import com.social.server.service.FriendshipRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public void create(FriendshipRequestDto dto) {
        FriendshipRequest request = new FriendshipRequest();
        request.setRequestTo(userRepository.getOne(dto.getToUserId()));
        request.setRequestFrom(userRepository.getOne(dto.getFromUserId()));
        repository.save(request);
    }

    @Override
    @Transactional
    public void accept(long friendshipRequestId) {
        FriendshipRequest friendshipRequest = findById(friendshipRequestId);
        friendshipRequest.setAccept(true);
        repository.save(friendshipRequest);
        friendService.addFriend(friendshipRequest.getRequestTo().getId(), friendshipRequest.getRequestFrom().getId());
        dialogService.create(friendshipRequest.getRequestFrom(), friendshipRequest.getRequestTo());
    }

    @Override
    public void decline(long friendshipRequestId) {
        repository.deleteById(friendshipRequestId);
    }

    @Override
    public List<FriendshipRequestDto> find(long userId) {
        return FriendshipRequestDto.of(repository.findByAcceptIsFalseAndRequestToId(userId));
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
