
package com.uphouse.monew.domain.comment.repository;

import com.uphouse.monew.domain.comment.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentLikeRepository extends JpaRepository<CommentLike, UUID> {
}
