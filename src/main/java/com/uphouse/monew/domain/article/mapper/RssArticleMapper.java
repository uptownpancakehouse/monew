package com.uphouse.monew.domain.article.mapper;

import com.rometools.rome.feed.synd.SyndEntry;
import com.uphouse.monew.domain.article.dto.RssArticle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RssArticleMapper {
    @Mapping(source = "title", target = "title")
    @Mapping(source = "link", target = "link")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "description.value", target = "description")
    @Mapping(source = "publishedDate", target = "publishedDate")
    RssArticle toDto(SyndEntry entry);
}
