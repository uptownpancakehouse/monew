package com.uphouse.monew.domain.interest.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "interest")
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

}
