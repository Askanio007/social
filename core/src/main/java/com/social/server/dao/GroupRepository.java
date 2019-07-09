package com.social.server.dao;

import com.social.server.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Page<Group> findByUsersIdIn(long usersId, Pageable pageable);
    long countAllByUsersIdIn(long userId);
    boolean existsByIdAndUsersIdIn(long id, long usersId);

    @Query("select g from Group g where name like concat('%', :name, '%')")
    List<Group> searchByName(String name);

    @Query("select count(u) from Group g inner join g.users as u where g.id = :groupId")
    long countParticipant(@Param("groupId") long groupId);
}
