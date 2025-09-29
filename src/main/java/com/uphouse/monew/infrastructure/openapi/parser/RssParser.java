package com.uphouse.monew.infrastructure.openapi.parser;

import com.uphouse.monew.domain.article.dto.RssArticle;

import java.util.List;

public interface RssParser {
    List<RssArticle> parse(String xml);
}