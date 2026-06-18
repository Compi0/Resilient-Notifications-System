package com.tromfi.notifications.application.usecase;

import com.tromfi.notifications.application.usecase.command.CreateNotificationCommand;
import com.tromfi.notifications.application.usecase.result.NotificationAck;

public interface NotificationOrchestrationService {

    NotificationAck sendNotification(CreateNotificationCommand command);
}
