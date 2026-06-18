package com.tromfi.notifications.adapters.out.entity;

import com.tromfi.notifications.domain.model.Notification;
import com.tromfi.notifications.domain.model.enums.MessageTypes;
import com.tromfi.notifications.domain.model.enums.MessageUrgency;
import org.springframework.stereotype.Component;

@Component
public class NotificationEntityMapper {

    public NotificationEntity toNotificationEntity(Notification domain) {
        NotificationEntity entity = new NotificationEntity();

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

        //MessageUrgency messageUrgency = ;


        //TODO No usarlo para crear una nueva notificacion, si no parea rehidratarlo. Nuevo metodo
        Notification notification = Notification.fromPersistence();

        return notification;
    }

}
