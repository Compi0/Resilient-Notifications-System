package com.tromfi.notifications.adapters.in.rest.dto;

public record NotificationRequestDTO(
        String content,
        String urgency, //Pero aqui hay que tener en mente como se conecta con el enum, cuando viene y mapper
        String recipient,
        String messagingType
) {
}
