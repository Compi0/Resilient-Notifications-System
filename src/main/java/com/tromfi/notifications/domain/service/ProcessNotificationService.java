package com.tromfi.notifications.domain.service;

import com.tromfi.notifications.domain.model.Notification;
import org.springframework.stereotype.Service;

@Service
public class ProcessNotificationService {

    public boolean validateNotification(Notification notification) {

        if (notification == null) {
            return false;
        }
        return notification.isRetryingAvailable();
    }

    public Notification acceptNotification(Notification notification) {

        notification.markSent();

        return notification;
    }

    public Notification rejectNotification(Notification notification) {

        notification.calculateNextAttempt();

        return notification;
    }

    public Notification sendToDeadQueue(Notification notification) {

        // Todavia pensar como se va a aplicar

        return notification;
    }

}
