package com.uphouse.monew.domain.notification.repository;

import com.uphouse.monew.domain.interest.domain.Interest;
import com.uphouse.monew.domain.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
