package com.tromfi.notifications.adapters.out.persistence;

import com.tromfi.notifications.adapters.out.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataNotificationRepository extends JpaRepository<NotificationEntity, Long> {

    @Query(
            value = """
            SELECT *
            FROM notification_entity u
            WHERE (u.message_status = 'RETRYING' OR u.message_status = 'PENDING')
              AND u.attempt_count <= 5
              AND u.next_attempt_at <= NOW();
            """,
            nativeQuery = true
    )
    List<NotificationEntity> findAllPendingNotifications();
}
