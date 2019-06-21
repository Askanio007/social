package com.social.server.service.impl;

import com.social.server.dao.FriendshipRequestRepository;
import com.social.server.dao.UserRepository;
import com.social.server.dto.FriendshipRequestDto;
import com.social.server.entity.FriendshipRequest;
import com.social.server.service.DialogService;
import com.social.server.service.FriendService;
import com.social.server.service.FriendshipRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FriendshipRequestServiceImpl implements FriendshipRequestService {

    private final FriendshipRequestRepository friendshipRequestRepository;
    private final UserRepository userRepository;
    private final FriendService friendService;
    private final DialogService dialogService;

    @Autowired
    public FriendshipRequestServiceImpl(FriendshipRequestRepository friendshipRequestRepository,
                                        UserRepository userRepository,
                                        FriendService friendService,
                                        DialogService dialogService) {
        this.friendshipRequestRepository = friendshipRequestRepository;
        this.userRepository = userRepository;
        this.friendService = friendService;
        this.dialogService = dialogService;
    }

    @Override
    public void add(FriendshipRequestDto dto) {
        FriendshipRequest request = new FriendshipRequest();
        request.setRequestTo(userRepository.getOne(dto.getToUserId()));
        request.setRequestFrom(userRepository.getOne(dto.getFromUserId()));
        friendshipRequestRepository.save(request);
    }

    @Override
    @Transactional
    public void accept(long friendshipRequestId) {
        Optional<FriendshipRequest> requestOptional = friendshipRequestRepository.findById(friendshipRequestId);
        if (!requestOptional.isPresent()) {
            log.error("friendship request not found. Id = " + friendshipRequestId);
            return;
        }
        FriendshipRequest request = requestOptional.get();
        request.setAccept(true);
        friendshipRequestRepository.save(request);
        friendService.addFriend(request.getRequestTo().getId(), request.getRequestFrom().getId());
        dialogService.create(new HashSet<>(Arrays.asList(request.getRequestFrom(), request.getRequestTo())));
    }

    @Override
    public void decline(long friendshipRequestId) {
        friendshipRequestRepository.deleteById(friendshipRequestId);
    }

    @Override
    public List<FriendshipRequestDto> find(long userId) {
        return FriendshipRequestDto.of(friendshipRequestRepository.findByAcceptIsFalseAndRequestToId(userId));
    }

    @Override
    public boolean isFriendRequest(long rootUserId, long userId) {
        return friendshipRequestRepository.existsByRequestFromIdAndRequestToIdAndAcceptIsFalse(rootUserId, userId);
    }
}
