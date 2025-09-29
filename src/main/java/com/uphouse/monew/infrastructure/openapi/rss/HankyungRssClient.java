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
                    .onStatus(
                            status -> status.value() == 429, // HTTP 429 감지
                            response -> {
                                log.warn("한경 RSS 429 Too Many Requests - 이번 배치는 skip");
                                return Mono.empty();
                            }
                    )
                    .bodyToMono(String.class)
                    .retryWhen(Retry.backoff(3, Duration.ofSeconds(5))) // 최대 3번, 5초 간격
                    .block();

            return Optional.ofNullable(xml)
                    .filter(str -> !str.isBlank())   // null 또는 빈 문자열은 제외
                    .map(rssParser::parse)           // 파싱 시도
                    .orElseGet(() -> {
                        log.warn("한경 RSS 응답이 비어 있음. 파싱 스킵");
                        return List.of();
                    });
        } catch (Exception e) {
            log.error("Failed to fetch Hankyung RSS", e);
            return List.of();
        }
    }
}
