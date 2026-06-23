package com.tromfi.notifications.adapters.out.entity.mapper;

import com.tromfi.notifications.adapters.out.entity.NotificationEntity;
import com.tromfi.notifications.domain.model.Notification;
import com.tromfi.notifications.domain.model.enums.MessageStatus;
import com.tromfi.notifications.domain.model.enums.MessageTypes;
import com.tromfi.notifications.domain.model.enums.MessageUrgency;
import org.springframework.stereotype.Component;

@Component
public class NotificationEntityMapper {

    public NotificationEntity toNotificationEntity(Notification domain) {
        NotificationEntity entity = new NotificationEntity();

        entity.setId(domain.getId());
        entity.setContent(domain.getContent());
        entity.setAttemptCount(domain.getAttemptCount());
        entity.setRecipient(domain.getRecipient());

        entity.setMessageStatus(domain.getMessageStatus().name());
        entity.setMessageUrgency(domain.getMessageUrgency().name());
        entity.setMessageTypes(domain.getMessageTypes().name());

        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setNextAttemptAt(domain.getNextAttemptAt());

        return entity;

    }

    public Notification toNotification(NotificationEntity domain) {

        return Notification.fromPersistence(
                domain.getId(),
                domain.getContent(),
                domain.getAttemptCount(),
                domain.getRecipient(),
                MessageTypes.valueOf(domain.getMessageTypes()),
                MessageStatus.valueOf(domain.getMessageStatus()),
                MessageUrgency.valueOf(domain.getMessageUrgency()),
                domain.getCreatedAt(),
                domain.getUpdatedAt(),
                domain.getNextAttemptAt()
        );
    }

}
