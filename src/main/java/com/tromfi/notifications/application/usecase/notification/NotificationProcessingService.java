package com.tromfi.notifications.application.usecase.interfaces;

import com.tromfi.notifications.domain.model.Notification;

public interface NotificationProcessingService {

    void processNotification(Notification notification);

}
