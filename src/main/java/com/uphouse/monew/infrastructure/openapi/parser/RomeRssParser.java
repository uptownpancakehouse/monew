package com.uphouse.monew.infrastructure.openapi.parser;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.uphouse.monew.domain.article.dto.RssArticle;
import com.uphouse.monew.domain.article.mapper.RssArticleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RomeRssParser implements RssParser {

    private final RssArticleMapper mapper;

    @Override
    public List<RssArticle> parse(String xml) {
        try (StringReader reader = new StringReader(xml)) {

            SyndFeed feed = new SyndFeedInput().build(reader);

            return feed.getEntries().stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            log.error("RSS XML 파싱 실패.", e);
            return List.of();
        }
    }
}
