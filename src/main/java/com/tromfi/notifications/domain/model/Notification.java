package com.tromfi.notifications.domain.model;

import com.tromfi.notifications.domain.model.enums.MessageStatus;
import com.tromfi.notifications.domain.model.enums.MessageUrgency;
import lombok.Getter;


import java.time.LocalDateTime;

// Setter tampcoo se usa porque hay que proteger a la clase para que otras capas de hexagonal no lo modifiquen desde fuera
@Getter
public class Notification {
    // Aqui en hexagonal, no se usa Entity, ni Id de Jakarta, porque eso se puede cambiar despues
    private Long id; // El Id no se define aqui, porque se usara un mapper para pasar de Model a Entity y ahora si usarlo en bd
    private String content;
    private int retries;
    private String recipient;
    private String messagingType;

    private MessageStatus messageStatus;
    private MessageUrgency messageUrgency;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime nextAttemptAt;

    public Notification() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.nextAttemptAt = LocalDateTime.now();
        this.retries = 0;
        this.messageStatus = MessageStatus.PENDING;
    }

    // Exponential backoff es aqui y en el servicio, esto es para intentar que el worker vuelva a intentar ejecutarlo
    // Pero el del servicio es por si el servicio esta caido
    private String calculateNextAttempt(){

        return "";
    }

}
