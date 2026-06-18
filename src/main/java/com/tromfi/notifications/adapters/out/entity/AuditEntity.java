package com.tromfi.notifications.adapters.out.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class AuditEntity {

    @Id
    @GeneratedValue
    private Long id;

}
