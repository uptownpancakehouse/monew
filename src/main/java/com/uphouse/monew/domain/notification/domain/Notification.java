package com.uphouse.monew.domain.notification.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

}
