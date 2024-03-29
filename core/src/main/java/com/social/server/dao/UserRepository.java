package com.social.server.dao;

import com.social.server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    long countAllByFriendsIdIn(long userId);
    boolean existsByIdAndFriends(long id, User friend);

    @Query("select f from User u join u.friends f where u.id = :rootUserId")
    Page<User> findFriendsBy(@Param("rootUserId") long rootUserId, Pageable pageable);

    @Query("select u from User u where u.id <> :rootUserId and ( " +
            "(u.name like concat('%',:name,'%') and u.surname like concat('%',:surname,'%')) " +
            "or " +
            "(u.name like concat('%',:surname,'%') and u.surname like concat('%',:name,'%'))" +
            ")")
    Set<User> searchByFullName(@Param("rootUserId") long rootUserId,
                               @Param("name")String name,
                               @Param("surname")String surname);
}
