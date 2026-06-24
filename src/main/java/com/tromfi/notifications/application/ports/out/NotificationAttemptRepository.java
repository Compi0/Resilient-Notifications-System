package com.tromfi.notifications.application.ports.out;


import com.tromfi.notifications.domain.model.NotificationAttempt;

public interface NotificationAttemptRepository {

    // NADA DE ANOTACIONES, solo declaracion de metodos
    NotificationAttempt save(NotificationAttempt notifictionAudit);

    int obtainAttempts(Long id);

}
