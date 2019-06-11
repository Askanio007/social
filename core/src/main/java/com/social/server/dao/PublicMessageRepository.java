package com.social.server.dao;

import com.social.server.entity.PublicMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicMessageRepository extends JpaRepository<PublicMessage, Long> {
    List<PublicMessage> findByUserIdOrderByCreateDateDesc(Long userId);
    List<PublicMessage> findByGroupIdOrderByCreateDateDesc(Long groupId);
}
