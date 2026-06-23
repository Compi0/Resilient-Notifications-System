package com.tromfi.notifications.adapters.out.persistence;

import com.tromfi.notifications.adapters.out.entity.NotificationAttemptEntity;
import com.tromfi.notifications.adapters.out.entity.mapper.NotificationAttemptEntityMapper;
import com.tromfi.notifications.application.ports.out.AuditRepository;
import com.tromfi.notifications.domain.model.NotificationAttempt;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class NotificationAuditRepositoryAdapter implements AuditRepository {

    private final SpringDataNotificationAuditRepository springDataNotificationAuditRepository;
    private final NotificationAttemptEntityMapper notificationAttemptEntityMapper;

    @Override
    public NotificationAttempt save(NotificationAttempt notifictionAudit) {

        NotificationAttemptEntity notificationAttemptEntity = notificationAttemptEntityMapper.toNotificationAttemptEntity(notifictionAudit);

        NotificationAttemptEntity savedNotificationAttempt = springDataNotificationAuditRepository.save(notificationAttemptEntity);

        return null;
    }

    @Override
    public int obtainAttempts(Long id) {
        return 0;
    }

}
