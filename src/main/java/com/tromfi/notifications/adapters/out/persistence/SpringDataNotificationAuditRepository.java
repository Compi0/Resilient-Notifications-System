package com.tromfi.notifications.adapters.out.persistence;

import com.tromfi.notifications.adapters.out.entity.NotificationAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataNotificationAuditRepository extends JpaRepository<NotificationAttemptEntity, Long> {

    @Query(value = "SELECT * FROM notification_attempt_entity u WHERE u.fk_notification_id = :notificationId",
    nativeQuery = true)
    List<NotificationAttemptEntity> findByNotificationId(Long notificationId);
    /*
       para guardar un attempt necesitas asignarle una NotificationEntity con el id correspondiente
Ese último punto es importante: al crear un NotificationAttemptEntity, tienes que ponerle su notificación padre. Puede ser una entity completa o una referencia
con solo id, pero debe quedar seteado notificationEntity, porque de ahí sale el fk_notification_id.
     */
    //List<NotificationAttemptEntity> findByNotificationEntityId(Long notificationId); <- Es lo mismo que arriba

    @Query(value = "SELECT COUNT(*) FROM notification_attempt_entity u WHERE u.fk_notification_id = :id",
            nativeQuery = true)
    int obtainAttempts(Long id);

}
