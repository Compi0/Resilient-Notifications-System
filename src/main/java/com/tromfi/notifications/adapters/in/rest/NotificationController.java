package com.tromfi.notifications.adapters.in.rest;

import com.tromfi.notifications.adapters.in.rest.dto.NotificationRequestDTO;
import com.tromfi.notifications.adapters.in.rest.dto.NotificationResponseDTO;
import com.tromfi.notifications.adapters.in.rest.mapper.AckNotificationMapper;
import com.tromfi.notifications.adapters.in.rest.mapper.NotificationMapper;
import com.tromfi.notifications.application.usecase.NotificationOrchestrationService;
import com.tromfi.notifications.application.usecase.command.CreateNotificationCommand;
import com.tromfi.notifications.application.usecase.result.NotificationAck;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class NotificationController {

    private final NotificationOrchestrationService notificationOrchestrationService;

    @PostMapping("/notification")
    public ResponseEntity<NotificationResponseDTO> sendNotification(@RequestBody NotificationRequestDTO notificationRequestDTO) {


        // TODO Aqui se haria algunas validaciones

        CreateNotificationCommand notificationCommand = NotificationMapper.MAPPER.
                toCreateNotificationCommand(notificationRequestDTO);

        NotificationAck notificationAck = notificationOrchestrationService.sendNotification(notificationCommand);

        NotificationResponseDTO notificationResponseDTO = AckNotificationMapper.MAPPER.
                toNotificationResponseDTO(notificationAck);

        // Recordar eso del httpstatus, no recuerdo si dependia de esto o como se hacia si se tuvo un error?
        // ControllerAdvice
        return new ResponseEntity<>(notificationResponseDTO, HttpStatus.ACCEPTED);
    }

}
