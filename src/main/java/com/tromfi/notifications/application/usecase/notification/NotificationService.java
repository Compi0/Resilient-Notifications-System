package com.tromfi.notifications.application.usecase.interfaces;

import com.tromfi.notifications.application.usecase.command.CreateNotificationCommand;
import com.tromfi.notifications.application.usecase.result.NotificationAck;

public interface NotificationService {

    NotificationAck sendNotification(CreateNotificationCommand command);
}
