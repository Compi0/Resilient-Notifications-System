package com.tromfi.notifications.domain.model.enums;

import java.util.Arrays;

public enum MessageTypes {
    EMAIL,
    TEXT_MESSAGE;

    public static boolean isValidMessageType(String value) {

        return Arrays.stream(MessageTypes.values()).
                anyMatch(status -> status.name().equalsIgnoreCase(value));
    }

}
