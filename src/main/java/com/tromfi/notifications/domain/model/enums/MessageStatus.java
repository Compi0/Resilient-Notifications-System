package com.tromfi.notifications.domain.model.enums;

import java.util.Arrays;

public enum MessageStatus {
    PENDING, // ESTA SI ES NECESARIA?
    PROCESSING,
    SENT,
    RETRYING,
    FAILED;

    public static boolean isValidMessageStatus(String value) {

        return Arrays.stream(MessageStatus.values()).
                anyMatch(status -> status.name().equalsIgnoreCase(value));
    }

}
