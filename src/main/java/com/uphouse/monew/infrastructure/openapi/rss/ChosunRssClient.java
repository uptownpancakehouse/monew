package com.uphouse.monew.infrastructure.openapi.rss;

import com.uphouse.monew.domain.article.dto.RssArticle;
import com.uphouse.monew.infrastructure.openapi.parser.RssParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChosunRssClient {

    private static final String RSS_URL = "https://www.chosun.com/arc/outboundfeeds/rss/?outputType=xml";
    private final WebClient webClient;
    private final RssParser rssParser;

    public List<RssArticle> fetchArticles() throws Exception {
        String xml = webClient.get()
                .uri(RSS_URL)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return rssParser.parse(xml);
    }
}
