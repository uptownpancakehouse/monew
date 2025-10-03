package com.uphouse.monew.domain.interest.repository;

import com.uphouse.monew.domain.interest.domain.Interest;
import com.uphouse.monew.domain.interest.domain.UserInterest;
import com.uphouse.monew.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
    List<UserInterest> findByUser(User user);
    Optional<UserInterest> findByUserAndInterest(User user, Interest interest);
    int countByInterestAndSubscribedByMeTrue(Interest interest);
}
