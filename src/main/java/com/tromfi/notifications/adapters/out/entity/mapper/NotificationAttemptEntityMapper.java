package com.tromfi.notifications.adapters.out.entity.mapper;

import com.tromfi.notifications.adapters.out.entity.NotificationAttemptEntity;
import com.tromfi.notifications.adapters.out.entity.NotificationEntity;
import com.tromfi.notifications.domain.model.NotificationAttempt;
import com.tromfi.notifications.domain.model.enums.AuditState;
import com.tromfi.notifications.domain.model.enums.ProviderMessage;
import org.springframework.stereotype.Component;

@Component
public class NotificationAttemptEntityMapper {

    public NotificationAttemptEntity toNotificationAttemptEntity(NotificationAttempt notificationAttempt) {
        NotificationAttemptEntity notificationAttemptEntity = new NotificationAttemptEntity();

        NotificationEntity notificationEntity = new NotificationEntity(notificationAttempt.getNotificationId());

        //notificationAttemptEntity.setId(notificationAttempt.getId());
        notificationAttemptEntity.setNotificationEntity(notificationEntity);
        notificationAttemptEntity.setTimestampAttempt(notificationAttempt.getTimestampAttempt());
        notificationAttemptEntity.setProviderMessage(notificationAttempt.getProviderMessage().name());
        notificationAttemptEntity.setProviderErrorCode(notificationAttempt.getProviderErrorCode());
        notificationAttemptEntity.setStatus(notificationAttempt.getStatus().name());

        return notificationAttemptEntity;
    }

    public NotificationAttempt toNotificationAttempt(NotificationAttemptEntity notificationAttemptEntity) {
        return NotificationAttempt.createFromPersistence(
                notificationAttemptEntity.getId(),
                notificationAttemptEntity.getNotificationEntity().getId(),
                ProviderMessage.valueOf(notificationAttemptEntity.getProviderMessage()),
                notificationAttemptEntity.getProviderErrorCode(),
                AuditState.valueOf(notificationAttemptEntity.getStatus()),
                notificationAttemptEntity.getTimestampAttempt()
        );

    }

}
