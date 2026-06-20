package com.tromfi.notifications.application.usecase.notification;

import com.tromfi.notifications.application.ports.out.NotificationRepository;
import com.tromfi.notifications.application.usecase.notification.command.CreateNotificationCommand;
import com.tromfi.notifications.application.usecase.notification.result.NotificationAck;
import com.tromfi.notifications.domain.model.Notification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    // Se pone la interfaz, no la clase concreta, porque internamente se inyecta la clase concreta pero depende de la interfaz
    private final NotificationRepository notificationRepository;

    @Override
    public NotificationAck sendNotification(CreateNotificationCommand command) {

        // Primero valida, luego hace el notification, despues guarda

        Notification notification = Notification.createNotification(command.getContent(), command.getRecipient(),
                command.getMessagingType(), command.getUrgency());

        Notification savedNotification = notificationRepository.save(notification);

        NotificationAck notificationAck = new NotificationAck(savedNotification.getId(), "Test");


        /*

        Pasos
        1- Recibe el Command, realiza validaciones y lo guarda en la BD
        2- Una vez recibido regresa el HttpStatus OK

        3 - Despues se tiene la ejecucion del worker, en donde toma de la bd y va mandando mensajes
        Aunque aqui tengo la duda si esto debe de ser algo aparte, porque si no bloquearia que se regrese el estado
        luego luego al cliente


           Esta es la que uso aqui, depende de NotificationRepository
            Pero spring inyecta     NotificationRepositoryAdapter
                Que usa JPA
         */

        return notificationAck;
    }




}
