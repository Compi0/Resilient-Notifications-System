package com.tromfi.notifications.application.usecase.notification.command;

import com.tromfi.notifications.domain.model.enums.MessageTypes;
import com.tromfi.notifications.domain.model.enums.MessageUrgency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CreateNotificationCommand {

    private String content;
    private MessageUrgency urgency;
    private String recipient;
    private MessageTypes messagingType;

}
