package com.tromfi.notifications.adapters.in.rest;

import com.tromfi.notifications.adapters.in.rest.dto.NotificationRequestDTO;
import com.tromfi.notifications.adapters.in.rest.dto.NotificationResponseDTO;
import com.tromfi.notifications.adapters.in.rest.mapper.AckNotificationMapper;
import com.tromfi.notifications.adapters.in.rest.mapper.NotificationMapper;
import com.tromfi.notifications.application.usecase.notification.NotificationService;
import com.tromfi.notifications.application.usecase.notification.command.CreateNotificationCommand;
import com.tromfi.notifications.application.usecase.notification.result.NotificationAck;
import com.tromfi.notifications.domain.exception.InvalidFieldsException;
import com.tromfi.notifications.domain.model.enums.MessageTypes;
import com.tromfi.notifications.domain.model.enums.MessageUrgency;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/notification")
    public ResponseEntity<NotificationResponseDTO> sendNotification(@RequestBody NotificationRequestDTO notificationRequestDTO) {


        // TODO No se si esto hacerlo boolean para que hagamos validacion y si no es valido, mandar algo diferente
        validateFields(notificationRequestDTO);

        CreateNotificationCommand notificationCommand = NotificationMapper.MAPPER.
                toCreateNotificationCommand(notificationRequestDTO);

        NotificationAck notificationAck = notificationService.sendNotification(notificationCommand);

        NotificationResponseDTO notificationResponseDTO = AckNotificationMapper.MAPPER.
                toNotificationResponseDTO(notificationAck);

        // Recordar eso del httpstatus, no recuerdo si dependia de esto o como se hacia si se tuvo un error?
        // ControllerAdvice
        return new ResponseEntity<>(notificationResponseDTO, HttpStatus.ACCEPTED);
    }


    public void validateFields(NotificationRequestDTO notificationRequestDTO) {

        if (notificationRequestDTO.content() == null || notificationRequestDTO.content().isEmpty()) {
            throw new InvalidFieldsException("Notification content cannot be empty");
        }
        if (notificationRequestDTO.urgency() == null || notificationRequestDTO.urgency().isEmpty()) {
            throw new InvalidFieldsException("Notification urgency cannot be empty");
        }
        if (notificationRequestDTO.messagingType() == null || notificationRequestDTO.messagingType().isEmpty()) {
            throw new InvalidFieldsException("Notification messagingType cannot be empty");
        }
        if (notificationRequestDTO.recipient() == null || notificationRequestDTO.recipient().isEmpty()) {
            throw new InvalidFieldsException("Notification recipient cannot be empty");
        }

        if (!MessageTypes.isValidMessageType(notificationRequestDTO.messagingType())) {
            throw new InvalidFieldsException("Message type is not valid");
        }
        if (!MessageUrgency.isValidMessageUrgency(notificationRequestDTO.urgency())) {
            throw new InvalidFieldsException("Message urgency is not valid");
        }

    }

}
