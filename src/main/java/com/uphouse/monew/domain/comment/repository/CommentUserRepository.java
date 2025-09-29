package com.uphouse.monew.domain.comment.repository;

import com.uphouse.monew.domain.comment.domain.CommentUser;
import com.uphouse.monew.domain.comment.dto.request.CommentCursorRequest;
import com.uphouse.monew.domain.comment.dto.response.CommentCursorResponse;
import com.uphouse.monew.domain.comment.dto.response.CommentCursorResponse.Content;
import com.uphouse.monew.domain.comment.mapper.CommentUserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentCursorResponse findByArticleIdWithCursor(CommentCursorRequest req) {
        boolean isAscending = "ASC".equalsIgnoreCase(req.direction().toString());
        String comparator = isAscending ? ">" : "<";
        String order = isAscending ? "ASC" : "DESC";

        StringBuilder sql = new StringBuilder("""
            SELECT
                c.id              AS comment_id,
                c.article_id      AS article_id,
                c.content         AS content,
                c.like_count      AS like_count,
                c.liked_by_me     AS liked_by_me,
                c.created_at      AS created_at,
                u.id              AS user_id,
                u.nickname        AS nickname
            FROM comments c
            JOIN users u ON u.id = c.user_id
            WHERE c.article_id = ?
        """);

        List<Object> params = new ArrayList<>();
        params.add(req.articleId());

        if (req.cursor() != null) {
            sql.append(" AND c.created_at ").append(comparator).append(" ?");
            params.add(req.cursor());
        }

        sql.append(" ORDER BY c.").append(req.orderBy().getColumn()).append(" ").append(order);
        sql.append(" LIMIT ?");

        params.add(req.limit());

        List<CommentUser> commentUsers;
        try {
            commentUsers = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                new CommentUserRowMapper()
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("쿼리 실패");
        }
        List<Content> contents = Content.toContentList(commentUsers);

        Integer totalElement;
        try {
            totalElement = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM comments WHERE article_id = ?",
                Integer.class,
                req.articleId()
            );
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("totalElement 가져오는 쿼리 실패");
        }

        int size = contents.size();
        boolean hasNext = size > 0;
        String nextCursor = hasNext
            ? commentUsers.get(req.limit() - 1).createdAt().toString()
            : null;

        return new CommentCursorResponse(
            contents,
            nextCursor,
            nextCursor,
            size,
            totalElement,
            hasNext
        );
    }
}