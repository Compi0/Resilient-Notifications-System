package com.tromfi.notifications.adapters.out;

import com.tromfi.notifications.application.ports.out.MessageSender;
import com.tromfi.notifications.domain.model.Notification;
import org.springframework.stereotype.Service;

@Service
public class MockMessageServiceAdapter implements MessageSender {

    @Override
    public boolean sendMessage(Notification notification) {


        // Esto va a cambiar, es solo por el momento
        return true;
    }

    // Aqui falta agregar la llamada al servicio fake, lo que se hace, realizar la implementacion de
    // la interfaz y poner anotaciones de resiliencia

}
