package com.uphouse.monew.domain.interest.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity @Getter
@Table(name = "interests")
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private int subscriberCount;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Interest (String name, int subscriberCount) {
        this.name = name;
        this.subscriberCount = subscriberCount;
        this.createdAt = LocalDateTime.now();
    }
}
