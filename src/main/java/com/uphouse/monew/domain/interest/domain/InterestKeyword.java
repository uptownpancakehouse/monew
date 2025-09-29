package com.uphouse.monew.domain.interest.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "interest_keywords")
public class InterestKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "interest_id", nullable = false)
    private Interest interest;

    @ManyToOne
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keywords keywords;

}
