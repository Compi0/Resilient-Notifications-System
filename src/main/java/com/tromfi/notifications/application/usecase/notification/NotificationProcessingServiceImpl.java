package com.tromfi.notifications.application.usecase.notification;

import com.tromfi.notifications.application.ports.out.MessageSender;
import com.tromfi.notifications.application.ports.out.NotificationAttemptRepository;
import com.tromfi.notifications.application.ports.out.NotificationRepository;
import com.tromfi.notifications.application.ports.out.MessageSendResult;
import com.tromfi.notifications.domain.model.Notification;
import com.tromfi.notifications.domain.model.NotificationAttempt;
import com.tromfi.notifications.domain.model.enums.AuditState;
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
    private final MessageSender mockMessageService;
    private final NotificationAttemptRepository notificationAttemptRepository;

    @Override
    @Async(value = "backgroundTaskExecutor")
    @Transactional // Aqui falta poner toda la configuracion
    public void processNotification(Notification notification) {

        boolean savedId = processNotificationService.validateNotification(notification);

        if (!savedId) {
            notification.markFailed(); // Se debe de guardar
            processNotificationService.sendToDeadQueue(notification);
            notificationRepository.save(notification);
            System.out.println(Thread.currentThread().getName() + ": Failed Processing Notification");
            // Tener cuidado con esta excepcion porque puede generar rollback sin guardar el estado actualizado
            // Por ahora solo se hace return pero en un futuro habilitamos la excepcion
            return;
            //throw new InvalidFieldsException("Invalid notification processing");
        }



        /*

         Falta checar el estado actual de la BD, de la notificacion actual, por eso tiene sentido usar obtainedNotification
         Porque dos hilos pueden obtener el mismo notification, pero tal vez ya se cambio en la BD, entonces no tiene sentido
         Checar el viejo cuando se tiene el nuevo/actualizado, por eso se usa el obatinedNotification

        Si ya está SENT, FAILED, PROCESSING por otro flujo, o su nextAttemptAt todavía no toca, simplemente sales sin hacer nada.
        Eso es lo que significa “revalidar después del lock”.
         */

        Long currentId = notification.getId();

        // Aqui ya hacemos el locking
        Notification lockedNotification = notificationRepository.findByIdForUpdate(currentId);

        if(!lockedNotification.isEligibleForProcessing()){
            return; // No tiene caso seguir porque ya fue procesado por un hilo anterior
        }

        System.out.println(Thread.currentThread().getName() + ":Processing Notification");

        // Aqui se puede hacer el flush, pero puede ser mas complicado??
        lockedNotification.markProcessing();
        // Aqui ya se marca en processing, pero si no se hace el commit entonces no se observan cambios en la BD
        // En tiempo, real, esto es esperado o tenemos que modificarlo?

        MessageSendResult messageSendResult = mockMessageService.sendMessage(lockedNotification);
        // Pero necesito un mapper para que pase los enums

        Notification processedNotification = messageSendResult.status().equals(AuditState.SUCCESS) ?
                processNotificationService.acceptNotification(lockedNotification)
            : processNotificationService.rejectNotification(lockedNotification);


        NotificationAttempt notificationAttempt = NotificationAttempt.createNotificationAttempt(processedNotification.getId(),
                messageSendResult.providerMessage(), messageSendResult.providerErrorCode(),
                messageSendResult.status(), processedNotification.getUpdatedAt());

        System.out.println(Thread.currentThread().getName() + ": Is processing" + notificationAttempt.toString());

        if(messageSendResult.status() == AuditState.PERMANENT_FAILURE){
            processedNotification.markFailed();
        }

        notificationAttemptRepository.save(notificationAttempt);
        notificationRepository.save(processedNotification);

        // Podemos hacer alguna verificacion

    }

}
