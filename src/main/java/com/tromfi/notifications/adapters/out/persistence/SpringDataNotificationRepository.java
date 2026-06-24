package com.tromfi.notifications.adapters.out.persistence;

import com.tromfi.notifications.adapters.out.entity.NotificationEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
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

    @Lock(LockModeType.PESSIMISTIC_WRITE) // Esto se traduce en el for update
    @Query(
            value = "SELECT n FROM NotificationEntity n WHERE n.id = :id"
    ) // Inclusive podria no necesitarse esta query, porque el @Lock ya se encarga de hacer la query por ti + Hibernate
    //@QueryHints({}) Checar esto porque esto puede ayudar a las Exceptions
    NotificationEntity findByIdForUpdate(Long id);
}
