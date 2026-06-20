package com.tromfi.notifications.application.usecase.notification;

import com.tromfi.notifications.application.usecase.notification.command.CreateNotificationCommand;
import com.tromfi.notifications.application.usecase.notification.result.NotificationAck;

public interface NotificationService {

    NotificationAck sendNotification(CreateNotificationCommand command);
}
