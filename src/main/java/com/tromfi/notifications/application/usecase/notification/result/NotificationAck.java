package com.tromfi.notifications.application.usecase.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class NotificationAck {

    private Long id;
    private String message;

}
