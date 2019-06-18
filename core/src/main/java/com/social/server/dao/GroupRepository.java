package com.social.server.dao;

import com.social.server.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByUsersIdIn(long usersId);
    boolean existsByIdAndUsersIdIn(long id, long usersId);
    @Query("select g from Group g where name like concat('%', :name, '%')")
    List<Group> searchByName(String name);
}
