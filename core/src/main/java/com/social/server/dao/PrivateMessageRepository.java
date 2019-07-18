package com.social.server.dao;

import com.social.server.entity.PrivateMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> {
    List<PrivateMessage> findByDialogIdOrderByCreateDateDesc(long dialogId, Pageable pageable);

    @Modifying
    @Query(
            value = "UPDATE private_message SET read = true WHERE id = :messageId AND sender_id <> :userId",
            nativeQuery = true)
    int readMessage(@Param("userId") long userId, @Param("messageId") long messageId);

    @Modifying
    @Query(
            value = "UPDATE private_message SET read = true WHERE dialog_id = :dialogId AND sender_id <> :userId",
            nativeQuery = true)
    int readMessageByDialogId(@Param("userId") long userId, @Param("dialogId") long dialogId);

    @Query("select count(m) from PrivateMessage m join m.dialog d join d.users u where m.read = false and m.sender.id <> :userId and u.id = :userId")
    long countNotReadMessages(@Param(value = "userId") long userId);
}
