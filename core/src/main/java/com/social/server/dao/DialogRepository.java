package com.social.server.dao;

import com.social.server.entity.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {
    List<Dialog> findByUsersIdInOrderByDateLastMessageDesc(long rootUserId);
    boolean existsByUsersIdIn(Set<Long> usersId);
}
