package com.tromfi.notifications.adapters.in.rest.dto;

public record NotificationResponseDTO(
        Long id,
        String message
        // ID unico de rastreo
        // Estado inicial -> Pendiente
) {
}
