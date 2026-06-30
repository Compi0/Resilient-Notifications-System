package com.tromfi.notifications.adapters.out;

import com.tromfi.notifications.application.ports.out.MessageSender;
import com.tromfi.notifications.application.ports.out.MessageSendResult;
import com.tromfi.notifications.domain.model.Notification;
import com.tromfi.notifications.domain.model.enums.AuditState;
import com.tromfi.notifications.domain.model.enums.ProviderMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class MockMessageServiceAdapter implements MessageSender {

    private final Executor providerExecutor;

    public MockMessageServiceAdapter(@Qualifier("providerExecutor") Executor providerExecutor) {
        this.providerExecutor = providerExecutor;
    }

    @Override
    public CompletableFuture<MessageSendResult> sendMessage(Notification notification) throws InterruptedException {

        System.out.println("Este es el hilo antes del CompletableFuture " + Thread.currentThread().getName());
        /*
        Esto es para poder dejar que otro hilo del rateLimiterExecutor pueda tomar esta ejecucion y no uno del
        worker, para que tambien asi se pueda usar el TimeLimiter correctamente
         */
        return CompletableFuture.supplyAsync(() -> {
            try{
                return mockSendMessage(notification);
            } catch (InterruptedException e) { // Esto con el orTimeout no se ejecuta
                System.out.println("Lanzo excepcion de timeout: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }, providerExecutor).orTimeout(2, TimeUnit.SECONDS);
    }


    private MessageSendResult mockSendMessage(Notification notification) throws InterruptedException{
        System.out.println("Este es el hilo dentro del mockSendMessage " + Thread.currentThread().getName());

        MessageSendResult messageSendResult = null;

        int randomNum = ThreadLocalRandom.current().nextInt(1, 4 + 1);

        if(randomNum == 1){
            // Aqui sera success
            System.out.println(Thread.currentThread().getName() + ": Is Success");
            messageSendResult = new MessageSendResult(ProviderMessage.SUCCESS, "202", AuditState.SUCCESS);

        }else if(randomNum == 2){
            // Aqui sera timeout (Despues lo implementamos para que sea timeout para medir la resiliencia)
            System.out.println(Thread.currentThread().getName() + ": Is timeout");
            System.out.println("Antes del timeout");
            Thread.sleep(4000); // Para probar el timeout
            System.out.println("Aqui se retoma desde el hilo pero ya se regreso Timeout");

            messageSendResult = new MessageSendResult(ProviderMessage.TIMEOUT, "408", AuditState.TIMEOUT);

        }else if(randomNum == 3){
            // Fallo directo
            System.out.println(Thread.currentThread().getName() + ": Is 500");
            messageSendResult = new MessageSendResult(ProviderMessage.PROVIDER_500, "500", AuditState.TEMPORARY_FAILURE);
        }else  if(randomNum == 4){
            // Invalid recipient
            System.out.println(Thread.currentThread().getName() + ": Is permanent failure");
            messageSendResult = new MessageSendResult(ProviderMessage.INVALID_RECIPIENT, "400", AuditState.PERMANENT_FAILURE);
        }

        return messageSendResult;
    }


}
