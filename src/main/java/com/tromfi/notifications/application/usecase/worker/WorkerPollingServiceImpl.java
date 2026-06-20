package com.tromfi.notifications.application.usecase.worker;

import com.tromfi.notifications.application.ports.out.NotificationRepository;
import com.tromfi.notifications.application.usecase.notification.NotificationProcessingService;
import com.tromfi.notifications.domain.model.Notification;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkerPollingServiceImpl implements WorkerPollingService {

    private final NotificationRepository notificationRepository;
    private final NotificationProcessingService notificationProcessingService;
    // Aqui tambien se meteria el servicio de mensajes, no?

    // Aqui void checar porque, tiene que ver con los workers
    @Override
    @Scheduled(fixedRate = 60000)
    public void obtainNotifications() {

        List<Notification> pendingNotifications = notificationRepository.findAllPendingNotifications();

        System.out.println(pendingNotifications.size());

        for  (Notification notification : pendingNotifications) {
            notificationProcessingService.processNotification(notification);
        }

    }

}
