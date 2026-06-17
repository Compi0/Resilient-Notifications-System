package com.tromfi.notifications.adapters.out.persistance;

import com.tromfi.notifications.adapters.out.entity.NotificationEntityMapper;
import com.tromfi.notifications.application.ports.out.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component // Aqui se debe de volver a usar algo como Repository, porque en si no se implemea lo de SpringDataRepository, si no Spring no lo encuentra
public class NotificationRepositoryAdapter implements NotificationRepository{

    private final SpringDataNotificationRepository springDataNotificationRepository;
    private NotificationEntityMapper notificationEntityMapper;



}
