package com.social.server.dao;

import com.social.server.entity.Dialog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {
    Page<Dialog> findByUsersIdInOrderByDateLastMessageDesc(long rootUserId, Pageable page);

    @Query("select count(m) from Dialog d join d.messages m where m.read = false and m.sender.id <> :userId")
    long countNotReadMessages(@Param(value = "userId") long userId);

    @Query("select d from Dialog d join d.users as u where u.id in (:users) group by d.id having count(d) = 2")
    Dialog findOneByUsersId(Set<Long> users);
}
