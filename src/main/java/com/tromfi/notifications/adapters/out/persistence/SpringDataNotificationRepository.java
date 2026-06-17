package com.tromfi.notifications.adapters.out.persistance;

import com.tromfi.notifications.adapters.out.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataNotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
