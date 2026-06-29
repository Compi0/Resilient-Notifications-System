package com.tromfi.notifications.application.usecase.notification;

import com.tromfi.notifications.application.ports.out.MessageSendResult;
import com.tromfi.notifications.application.ports.out.NotificationAttemptRepository;
import com.tromfi.notifications.application.ports.out.NotificationRepository;
import com.tromfi.notifications.domain.model.Notification;
import com.tromfi.notifications.domain.model.NotificationAttempt;
import com.tromfi.notifications.domain.model.enums.AuditState;
import com.tromfi.notifications.domain.service.ProcessNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class NotificationDatabaseHandlingImpl {

    private final NotificationRepository notificationRepository;
    private final NotificationAttemptRepository notificationAttemptRepository;
    private final ProcessNotificationService processNotificationService;


    @Transactional // Este debe de ser para bloquear la transaccion
    public Notification obtainAndLockRecord(Notification notification) {

        Long currentId = notification.getId(); // <- Tal vez esto hacerlo desde el principio y hacer el validate notification con el locked o el actualizado de la bd

        // Aqui ya hacemos el locking y se usa la informacion mas reciente de la BD, no de la que se paso porque otros hilos
        // Pueden tener informacion desactualizada
        Notification lockedNotification = notificationRepository.findByIdForUpdate(currentId);

        if(!lockedNotification.isEligibleForProcessing()){ // Aqui es en donde es el processing
//            if(!processNotificationService.validateNotification(lockedNotification)){
//                lockedNotification.markFailed();
//                notificationRepository.save(lockedNotification);
//                System.out.println(Thread.currentThread().getName() + ": Failed Processing Notification");
//            }
            return null; // No tiene caso seguir porque ya fue procesado por un hilo anterior o esta en ese momento en procesamiento
        }

        lockedNotification.markProcessing();

        //            //throw new InvalidFieldsException("Invalid notification processing");


        System.out.println(Thread.currentThread().getName() + ":Processing Notification");

        notificationRepository.save(lockedNotification);

        return lockedNotification;
    }

    @Transactional
    public void persistInformationDB(MessageSendResult messageSendResult, Notification lockedNotification) {

        Notification processedNotification = messageSendResult.status().equals(AuditState.SUCCESS) ?
                processNotificationService.acceptNotification(lockedNotification)
                : processNotificationService.rejectNotification(lockedNotification);

        if(messageSendResult.status() == AuditState.PERMANENT_FAILURE){
            processedNotification.markFailed();
        }

        NotificationAttempt notificationAttempt = NotificationAttempt.createNotificationAttempt(processedNotification.getId(),
                messageSendResult.providerMessage(), messageSendResult.providerErrorCode(),
                messageSendResult.status(), processedNotification.getUpdatedAt());

        System.out.println(Thread.currentThread().getName() + ": Is processing" + notificationAttempt.toString());


        notificationAttemptRepository.save(notificationAttempt);
        notificationRepository.save(processedNotification);

    }

}
