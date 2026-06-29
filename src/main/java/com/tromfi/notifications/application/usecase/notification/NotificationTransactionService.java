package com.tromfi.notifications.application.usecase.notification;

import com.tromfi.notifications.application.ports.out.MessageSendResult;
import com.tromfi.notifications.domain.model.Notification;

public interface NotificationTransactionService {

    Notification obtainAndLockRecord(Notification notification);

    void persistInformationDB(MessageSendResult messageSendResult, Notification lockedNotification);

}
