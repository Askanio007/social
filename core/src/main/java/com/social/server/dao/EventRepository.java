package com.social.server.dao;

import com.social.server.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByUserFriendsIdInOrderByDateDesc(long userId);
}
