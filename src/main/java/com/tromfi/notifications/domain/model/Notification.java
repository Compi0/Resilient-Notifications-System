package com.tromfi.notifications.domain.model;

import com.tromfi.notifications.domain.exception.InvalidFieldsException;
import com.tromfi.notifications.domain.model.enums.MessageStatus;
import com.tromfi.notifications.domain.model.enums.MessageTypes;
import com.tromfi.notifications.domain.model.enums.MessageUrgency;
import lombok.Getter;


import java.time.LocalDateTime;

// Setter tampcoo se usa porque hay que proteger a la clase para que otras capas de hexagonal no lo modifiquen desde fuera
@Getter
public class Notification {
    // TODO En un futuro hay que ponerlo para que lo tome desde app properties
    private final int MAX_ATTEMPTS = 5;
    // Aqui en hexagonal, no se usa Entity, ni Id de Jakarta, porque eso se puede cambiar despues
    //private Long id; // El Id no se define aqui, porque se usara un mapper para pasar de Model a Entity y ahora si usarlo en bd
    private String content;
    private int attemptCount;

    private String recipient;
    private MessageTypes messageTypes;

    private MessageStatus messageStatus;
    private MessageUrgency messageUrgency;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime nextAttemptAt;

    // No se puede instanciar, solo por medio del factory
    private Notification(){

    }// El dominio deberia de hacerlo de una forma si no, no se hace
    // Se debe de obligar a que sea 100% asi, o sea valido como se espera

    // Factory para crear objetos validos, siempre
    public static Notification createNotification(String content, String recipient, MessageTypes messageTypes,
                                            MessageUrgency messageUrgency){
        Notification notification = new Notification();

        boolean valid = notification.validateFields(content, recipient, messageTypes, messageUrgency);
        if(!valid){
            // TODO Mejorar el contenido del error para que sepa que le falto
            throw new InvalidFieldsException("Invalid Notification Content");
        }

        // Cambiarlo por builder?
        // No olivdar el Id, pero eso en donde se obtiene?
        notification.content = content;
        notification.recipient = recipient;
        notification.messageTypes = messageTypes;
        notification.messageUrgency = messageUrgency;
        notification.createdAt = LocalDateTime.now();
        notification.updatedAt = LocalDateTime.now();
        notification.nextAttemptAt = LocalDateTime.now();
        notification.attemptCount = 0;
        notification.messageStatus = MessageStatus.PENDING;

        return notification;
    }

    private boolean validateFields(String content, String recipient, MessageTypes messageTypes,
                           MessageUrgency messageUrgency){
        //Basic validation, whe can do more
        return !content.isEmpty() && !recipient.isEmpty() && messageTypes != null && messageUrgency != null;
    }

    // Exponential backoff es aqui y en el servicio, esto es para intentar que el worker vuelva a intentar ejecutarlo
    // Pero el del servicio es por si el servicio esta caido
    public LocalDateTime calculateNextAttempt(){
        this.attemptCount++;

        if(this.attemptCount > this.MAX_ATTEMPTS){
            this.messageStatus = MessageStatus.FAILED;
        }
        else{
            double timeoff = Math.pow(2,this.attemptCount-1);
            this.nextAttemptAt = LocalDateTime.now().plusMinutes(Math.round(timeoff));
        }

        return this.nextAttemptAt;
    }


    public boolean isRetryingAvailable(){
        // TODO Primero hay que validar null, luego vacío.
        return this.messageStatus != MessageStatus.FAILED && this.attemptCount <= this.MAX_ATTEMPTS;
    }

    // En vez de setters se usa eso entonces deben de ser publicos
    public void markSent(){
        this.messageStatus = MessageStatus.SENT;
    }

    // Mejor que actualizar el timestamp sea interno a todo esto, porque si no podria ser como un setter disfrazado

//    public void setUpdatedAt(){
//        this.updatedAt = LocalDateTime.now();
//    }


}
