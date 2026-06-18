package com.tromfi.notifications.adapters.out.entity;

import com.tromfi.notifications.domain.model.enums.MessageStatus;
import com.tromfi.notifications.domain.model.enums.MessageTypes;
import com.tromfi.notifications.domain.model.enums.MessageUrgency;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private int attemptCount;

    private String recipient;
    private String messageTypes;

    //@Enumerated(EnumType.STRING) CHECAR ESO
    private String messageStatus;
    private String messageUrgency;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime nextAttemptAt;

}
