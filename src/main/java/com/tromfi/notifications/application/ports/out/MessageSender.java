package com.tromfi.notifications.application.ports.out;

import com.tromfi.notifications.domain.model.Notification;

public interface MockMessageService {

    boolean sendMessage(Notification notification);

}
