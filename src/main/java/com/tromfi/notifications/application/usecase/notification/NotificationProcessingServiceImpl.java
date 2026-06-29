package com.tromfi.notifications.application.usecase.notification;

import com.tromfi.notifications.application.ports.out.MessageSender;
import com.tromfi.notifications.application.ports.out.MessageSendResult;
import com.tromfi.notifications.domain.model.Notification;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class NotificationProcessingServiceImpl implements NotificationProcessingService {

    private final MessageSender mockMessageService;
    private final NotificationTransactionServiceImpl notificationDatabaseHandlingImpl;

    // Este va a ser el metodo principal ahora
    @Override
    @Async(value = "backgroundTaskExecutor")
    public void processNotification(Notification notification) throws InterruptedException {

        Notification lockedNotification = notificationDatabaseHandlingImpl.obtainAndLockRecord(notification);

        // Supongo que aqui requiere aun un mejor manejo de los estados y transacciones, porque si pasa algo
        // en el servicio externo, se necesita terminar la transaccion y desbloquear la row que se hizo al inicio
        if  (lockedNotification == null) {
            return;
        }
//        CompletableFuture<MessageSendResult> messageSendResultFuture = executeServiceMessage(lockedNotification);
        MessageSendResult messageSendResult = mockMessageService.sendMessage(lockedNotification);

        // Ver la forma correcta de obtener el resultado del mensaje para hacer la ultima ejecucion, no se puede hacer como una pausa hasta que se obtenga?

        notificationDatabaseHandlingImpl.persistInformationDB(messageSendResult,  lockedNotification);

    }


    // Para el rate limiter y la llamada a la API y que no quede bloqueado el servicio de la BD con transacciones
    public CompletableFuture<MessageSendResult> executeServiceMessage(Notification lockedNotification) throws InterruptedException {

        //CompletableFuture<MessageSendResult> future = mockMessageService.sendMessage(lockedNotification);
        // Probablemente el servicio externo pueda lanzar excepcion, entonces aqui se cachan para que se pueda persistir correctamente

        //return future; <- Esto se va a cambiar porque debemos de usar el Timeout, que sera un future
        return null;
    }

}
