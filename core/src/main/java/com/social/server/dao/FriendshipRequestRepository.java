package com.social.server.dao;

import com.social.server.entity.FriendshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long> {
    List<FriendshipRequest> findByAcceptIsFalseAndRequestToId(long toUserId);
    Boolean existsByRequestFromIdAndRequestToIdAndAcceptIsFalse(long rootUserId, long userId);
}
