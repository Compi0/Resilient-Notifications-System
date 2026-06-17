package com.tromfi.notifications.application.ports.out;


import com.tromfi.notifications.adapters.out.entity.NotificationEntity;
import com.tromfi.notifications.domain.model.Notification;

// Aqui yo defino lo que NECESITO, unicamente
public interface NotificationRepository  {

    // NADA DE ANOTACIONES, solo declaracion de metodos
    Long save(Notification notification);

}
