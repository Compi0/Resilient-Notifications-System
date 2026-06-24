package com.tromfi.notifications.application.ports.out;

import com.tromfi.notifications.domain.model.Notification;

// Este no se pone que es mock porque esta ya es una implementacion que debe de tener el programa, el MockService se puede cambiar
// Pero esto no, porque son los metodos que debe de tener para funcionar correctamente
public interface MessageSender {

    MessageSendResult sendMessage(Notification notification);

}
