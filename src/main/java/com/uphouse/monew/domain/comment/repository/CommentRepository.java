package com.uphouse.monew.domain.comment.repository;

import com.uphouse.monew.domain.comment.domain.Comment;
import com.uphouse.monew.domain.interest.domain.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
