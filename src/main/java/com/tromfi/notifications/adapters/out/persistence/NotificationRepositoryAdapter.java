package com.tromfi.notifications.adapters.out.persistence;

import com.tromfi.notifications.adapters.out.entity.NotificationEntity;
import com.tromfi.notifications.adapters.out.entity.NotificationEntityMapper;
import com.tromfi.notifications.application.ports.out.NotificationRepository;
import com.tromfi.notifications.domain.model.Notification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component // Aqui se debe de volver a usar algo como Repository, porque en si no se implemea lo de SpringDataRepository, si no Spring no lo encuentra
public class NotificationRepositoryAdapter implements NotificationRepository{

    private final SpringDataNotificationRepository springDataNotificationRepository;
    private NotificationEntityMapper notificationEntityMapper;


    @Override
    public Long save(Notification notification) {

        NotificationEntity notificationEntity = notificationEntityMapper.toNotificationEntity(notification);

        // Usar Optional para checar esto
        NotificationEntity savedEntity = springDataNotificationRepository.save(notificationEntity);

        return savedEntity.getId();

    }

    @Override
    public List<Notification> findAllPendingNotifications() {

        List<NotificationEntity> notificationEntities = springDataNotificationRepository.findAllPendingNotifications();
        List<Notification> notifications = new ArrayList<>();

        for (NotificationEntity notificationEntity : notificationEntities) {
            notifications.add(notificationEntityMapper.toNotification(notificationEntity));
        }

        //Checar no este vacio

        return notifications;
    }
}
