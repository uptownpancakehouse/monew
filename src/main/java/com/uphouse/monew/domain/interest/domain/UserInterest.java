package com.uphouse.monew.domain.interest.domain;

import com.uphouse.monew.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_interests")
public class UserInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "interest_id", nullable = false)
    private Interest interest;

    private Boolean subscribedByMe;

    public UserInterest(User user, Interest interest, Boolean subscribedByMe) {
        this.user = user;
        this.interest = interest;
        this.subscribedByMe = subscribedByMe;
    }

    public void interestSubscribe(boolean subscribedByMe) {
        this.subscribedByMe = !subscribedByMe;
    }
}
