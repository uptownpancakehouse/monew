package com.uphouse.monew.domain.interest.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "interest")
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
