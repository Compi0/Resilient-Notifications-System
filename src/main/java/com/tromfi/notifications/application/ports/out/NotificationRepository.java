package com.tromfi.notifications.application.ports.out;


import com.tromfi.notifications.domain.model.Notification;

import java.util.List;

// Aqui yo defino lo que NECESITO, unicamente
public interface NotificationRepository  {

    // NADA DE ANOTACIONES, solo declaracion de metodos
    Notification save(Notification notification);

    List<Notification> findAllPendingNotifications();

    //Long update(Notification notification);

    Notification findByIdForUpdate(Long id);

}
