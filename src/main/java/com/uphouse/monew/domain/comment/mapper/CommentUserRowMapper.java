package com.uphouse.monew.domain.comment.mapper;

import com.uphouse.monew.domain.comment.domain.CommentUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CommentUserRowMapper implements RowMapper<CommentUser> {

    @Override
    public CommentUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CommentUser(
            UUID.fromString(rs.getString("comment_id")),
            UUID.fromString(rs.getString("article_id")),
            rs.getString("content"),
            rs.getInt("like_count"),
            rs.getBoolean("liked_by_me"),
            rs.getTimestamp("created_at").toLocalDateTime(),
            UUID.fromString(rs.getString("user_id")),
            rs.getString("nickname")
        );
    }
}