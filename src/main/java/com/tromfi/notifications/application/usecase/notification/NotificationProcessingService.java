package com.tromfi.notifications.application.usecase.notification;

import com.tromfi.notifications.domain.model.Notification;

public interface NotificationProcessingService {

    void processNotification(Notification notification);

}
