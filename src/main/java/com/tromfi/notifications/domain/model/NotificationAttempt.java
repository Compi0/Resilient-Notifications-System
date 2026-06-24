package com.tromfi.notifications.domain.model;

import com.tromfi.notifications.domain.model.enums.AuditState;
import com.tromfi.notifications.domain.model.enums.ProviderMessage;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class NotificationAttempt {

    private Long id;
    private Long notificationId;
    private LocalDateTime timestampAttempt;
    private ProviderMessage providerMessage;
    private String providerErrorCode;
    private AuditState status;

    private NotificationAttempt(){}

    public static NotificationAttempt createNotificationAttempt(
            Long notificationId, ProviderMessage providerMessage, String providerErrorCode,  AuditState status,
            LocalDateTime timestampAttempt
    ){
        NotificationAttempt notificationAttempt = new NotificationAttempt();

        notificationAttempt.id = null;
        notificationAttempt.notificationId = notificationId;
        notificationAttempt.providerMessage = providerMessage;
        notificationAttempt.providerErrorCode = providerErrorCode;
        notificationAttempt.status = status;
        notificationAttempt.timestampAttempt = timestampAttempt;

        return notificationAttempt;
    }


    public static NotificationAttempt createFromPersistence(
            Long id, Long notificationId, ProviderMessage providerMessage, String providerErrorCode,  AuditState status,
            LocalDateTime timestampAttempt
    ){

        NotificationAttempt notificationAttempt = new NotificationAttempt();

        notificationAttempt.id = id;
        notificationAttempt.notificationId = notificationId;
        notificationAttempt.providerMessage = providerMessage;
        notificationAttempt.providerErrorCode = providerErrorCode;
        notificationAttempt.status = status;
        notificationAttempt.timestampAttempt =timestampAttempt;

        return notificationAttempt;
    }

}
