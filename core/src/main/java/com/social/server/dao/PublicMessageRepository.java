package com.social.server.dao;

import com.social.server.entity.PublicMessage;
import com.social.server.entity.PublicMessageRecipientType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicMessageRepository extends JpaRepository<PublicMessage, Long> {
    Page<PublicMessage> findAllByRecipientIdAndRecipientType(Long userId, PublicMessageRecipientType type, Pageable page);
}
