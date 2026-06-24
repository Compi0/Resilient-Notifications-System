package com.tromfi.notifications.adapters.out.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    public NotificationEntity(Long id) {
        this.id = id;
    }

}
