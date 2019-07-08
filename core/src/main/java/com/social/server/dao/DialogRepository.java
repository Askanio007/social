package com.social.server.dao;

import com.social.server.entity.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {
    List<Dialog> findByUsersIdInOrderByDateLastMessageDesc(long rootUserId);
    boolean existsByUsersIdIn(Set<Long> usersId);
    @Query("select d from Dialog d join d.users as u where u.id in (:users) group by d.id having count(d) = 2")
    Dialog findOneByUsersId(Set<Long> users);
}
