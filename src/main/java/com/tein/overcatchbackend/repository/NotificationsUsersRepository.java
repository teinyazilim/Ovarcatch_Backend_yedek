package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.NotificationsUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationsUsersRepository  extends JpaRepository<NotificationsUsers, Long> {

    @Query(value = "SELECT * FROM notifications_users where notification_id = :notiId group by client_id;", nativeQuery = true)
    List<NotificationsUsers> getClientByNotifications(Long notiId);
}
