package com.tromfi.notifications.application.usecase.notification.result;

import com.tromfi.notifications.domain.model.enums.AuditState;
import com.tromfi.notifications.domain.model.enums.ProviderMessage;

public record MessageSendResult(
        ProviderMessage providerMessage,
        String providerErrorCode,
        AuditState status
){

}
