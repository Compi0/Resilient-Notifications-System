package com.tromfi.notifications.application.usecase.notification;

import com.tromfi.notifications.application.ports.out.MessageSender;
import com.tromfi.notifications.application.ports.out.MessageSendResult;
import com.tromfi.notifications.domain.model.Notification;
import com.tromfi.notifications.domain.model.enums.AuditState;
import com.tromfi.notifications.domain.model.enums.ProviderMessage;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class NotificationProcessingServiceImpl implements NotificationProcessingService {

    private final MessageSender mockMessageService;
    private final NotificationTransactionService notificationDatabaseHandling;

    // Este va a ser el metodo principal ahora
    @Override
    @Async(value = "backgroundTaskExecutor")
    public void processNotification(Notification notification) throws InterruptedException {

        Notification lockedNotification = notificationDatabaseHandling.obtainAndLockRecord(notification);

        // Supongo que aqui requiere aun un mejor manejo de los estados y transacciones, porque si pasa algo
        // en el servicio externo, se necesita terminar la transaccion y desbloquear la row que se hizo al inicio
        if  (lockedNotification == null) {
            return;
        }

        // Aqui todavia no tenemos ningun resultado, solo decimos que en algun momento tendremos un resultado
        CompletableFuture<MessageSendResult> messageSendResult = mockMessageService.sendMessage(lockedNotification);


        CompletableFuture<Void> completedProcess = messageSendResult

                // Este se ejecuta si hay un error, en este caso lo manejamos de que fue el timeout
                // Esto hace que al final siempre se ejecute el persistInformationDB que es lo que queremos
                .exceptionally(ex -> {

                    System.out.println(ex.getClass().getName()); // Aqui debemos de regresar un objeto MessageSendResult porque eso es lo que se espera obtener
                    // Aqui se maneja que es lo que queremos devolver
                    return new MessageSendResult(ProviderMessage.TIMEOUT, "408", AuditState.TIMEOUT);

                })

                // thenAccept = Cuando se tenga el resultado entonces se ejecuta lo que esta dentro del lambda
                .thenAccept(
                result -> {
                    System.out.println("Este es el hilo despues del mockSendMessage " + Thread.currentThread().getName());
                    System.out.println("The result is:" + result);
                    notificationDatabaseHandling.persistInformationDB(result, lockedNotification);

                }
        );


    }


}
