package com.tromfi.notifications.adapters.out.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class NotificationAttemptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_notification_id", referencedColumnName = "id")
    private NotificationEntity notificationEntity;

    private LocalDateTime timestampAttempt;
    private String providerMessage;
    private String providerErrorCode;
    private String status;

}
