package com.social.server.dao;

import com.social.server.entity.FriendshipRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long> {
    Page<FriendshipRequest> findAllByAcceptIsFalseAndRequestToId(long toUserId, Pageable pageable);
    Boolean existsByRequestFromIdAndRequestToIdAndAcceptIsFalse(long rootUserId, long userId);
    long countByRequestToIdAndAcceptIsFalse(long userId);
}
