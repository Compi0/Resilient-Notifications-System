package com.tromfi.notifications.application.usecase.implementation;

import com.tromfi.notifications.application.ports.out.NotificationRepository;
import com.tromfi.notifications.application.usecase.interfaces.NotificationProcessingService;
import com.tromfi.notifications.domain.exception.InvalidFieldsException;
import com.tromfi.notifications.domain.model.Notification;
import com.tromfi.notifications.domain.service.ProcessNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class NotificationProcessingServiceImpl implements NotificationProcessingService {

    private final ProcessNotificationService processNotificationService;
    private final NotificationRepository notificationRepository;

    @Override
    @Async(value = "backgroundTaskExecutor")
    @Transactional // Aqui falta poner toda la configuracion
    public void processNotification(Notification notification) {


        boolean savedId = processNotificationService.validateNotification(notification);

        if (!savedId) {
            throw new InvalidFieldsException("Invalid notification");
        }

        /*

        Aqui ya se obtiene una notification valida para procesar, pero puede que este bien checar
        que en realidad si esta lista para procesar

        Si es valida, entonces se debe de llamar al servicio externo y esperar la respuesta que puede ser 2

       1. Valida, entonces se cambia el valor de la notificacion
        - Actualizar BD
       2. Se rechaza, hace los cambios pertinentes para ver si se vuelve a intentar
        - Actualizar BD
       3. Aqui tambien se llamaria el loggin y se haria algo probablemente

         */


        // Despues de todo el procedimiento actualizamos

        Notification savedIdNotification = notificationRepository.save(notification);

        // Podemos hacer alguna verificacion


    }

}
