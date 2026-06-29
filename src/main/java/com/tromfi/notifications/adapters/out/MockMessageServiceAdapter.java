package com.tromfi.notifications.adapters.out;

import com.tromfi.notifications.application.ports.out.MessageSender;
import com.tromfi.notifications.application.ports.out.MessageSendResult;
import com.tromfi.notifications.domain.model.Notification;
import com.tromfi.notifications.domain.model.enums.AuditState;
import com.tromfi.notifications.domain.model.enums.ProviderMessage;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class MockMessageServiceAdapter implements MessageSender {

    @Override
    public MessageSendResult sendMessage(Notification notification) throws InterruptedException {

        MessageSendResult messageSendResult = mockSendMessage(notification);

        return messageSendResult;
    }

    private MessageSendResult mockSendMessage(Notification notification) throws InterruptedException{
        MessageSendResult messageSendResult = null;

        int randomNum = ThreadLocalRandom.current().nextInt(1, 4 + 1);

        if(randomNum == 1){
            // Aqui sera success
            System.out.println(Thread.currentThread().getName() + ": Is Success");
            messageSendResult = new MessageSendResult(ProviderMessage.SUCCESS, "202", AuditState.SUCCESS);

        }else if(randomNum == 2){
            // Aqui sera timeout (Despues lo implementamos para que sea timeout para medir la resiliencia)
            System.out.println(Thread.currentThread().getName() + ": Is timeout");
            //Thread.sleep(4000);
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
