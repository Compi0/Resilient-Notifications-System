package com.tromfi.notifications.application.usecase.worker;

public interface WorkerPollingService {

    void obtainNotifications() throws InterruptedException;

}
