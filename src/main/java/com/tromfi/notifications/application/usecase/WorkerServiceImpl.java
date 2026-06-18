package com.tromfi.notifications.application.usecase;

import com.tromfi.notifications.application.ports.out.NotificationRepository;
import com.tromfi.notifications.domain.model.Notification;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WokerkServiceImpl implements WorkerService {

    private final NotificationRepository notificationRepository;


    // Aqui void checar porque, tiene que ver con los workers
    @Override
    @Scheduled(fixedRate = 60000)
    public void obtainNotifications() {

        List<Notification> pendingNotifications = notificationRepository.findAllPendingNotifications();


        //return List.of();
    }

    // Process notification

    // Checar si es failed, cambiar y volver a guardar

    // Update DB
}
