package com.uphouse.monew.infrastructure.openapi.rss;

import com.uphouse.monew.domain.article.dto.RssArticle;
import com.uphouse.monew.infrastructure.openapi.parser.RssParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class HankyungRssClient {

    private static final String RSS_URL = "https://www.hankyung.com/feed/all-news";
    private final WebClient webClient;
    private final RssParser rssParser;

    public List<RssArticle> fetchArticles() {

        try {
            String xml = webClient.get()
                    .uri(RSS_URL)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return rssParser.parse(xml);
        }
        catch (Exception e) {
            log.warn("한경 RSS 가져오기 실패, 스킵합니다: {}", e.getMessage());
            return List.of(); // 빈 리스트 반환해서 Reader 쪽에서 null 처리
        }
    }
}
