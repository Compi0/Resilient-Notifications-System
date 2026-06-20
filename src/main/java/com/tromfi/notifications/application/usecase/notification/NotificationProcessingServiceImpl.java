package com.tromfi.notifications.application.usecase.notification;

import com.tromfi.notifications.adapters.out.MockMessageService;
import com.tromfi.notifications.application.ports.out.NotificationRepository;
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

    // Esto se va a cambiar para que se adapte a hexagonal bien con la interfaz y adapter y todo
    private final MockMessageService mockMessageService;

    @Override
    @Async(value = "backgroundTaskExecutor")
    @Transactional // Aqui falta poner toda la configuracion
    public void processNotification(Notification notification) {


        boolean savedId = processNotificationService.validateNotification(notification);

        if (!savedId) {
            notification.markFailed(); // Se debe de guardar
            processNotificationService.sendToDeadQueue(notification);
            notificationRepository.save(notification);

            // Tener cuidado con esta excepcion porque puede generar rollback sin guardar el estado actualizado
            throw new InvalidFieldsException("Invalid notification processing");
        }

        notification.markProcessing();

        boolean isSent = mockMessageService.sendMessage(notification);

        Notification processedNotification;

        if(!isSent){
            processedNotification = processNotificationService.rejectNotification(notification);
        }else{
            processedNotification = processNotificationService.acceptNotification(notification);
        }

        Notification savedNotification = notificationRepository.save(processedNotification);

        // Podemos hacer alguna verificacion


    }

}
