package com.uphouse.monew.domain.user.domain;

import com.uphouse.monew.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@FieldNameConstants(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String nickname;

    private String password;

    public User(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
}
