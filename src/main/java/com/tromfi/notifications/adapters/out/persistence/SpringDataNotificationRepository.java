package com.tromfi.notifications.adapters.out.persistence;

import com.tromfi.notifications.adapters.out.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataNotificationRepository extends JpaRepository<NotificationEntity, Long> {

}
