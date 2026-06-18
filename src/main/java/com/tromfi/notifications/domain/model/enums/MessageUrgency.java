package com.tromfi.notifications.domain.model.enums;

import java.util.Arrays;

public enum MessageUrgency {
    LOW,
    MEDIUM,
    HIGH;

    public static boolean isValidMessageUrgency(String value) {

        return Arrays.stream(MessageUrgency.values()).
                anyMatch(status -> status.name().equalsIgnoreCase(value));
    }

}
