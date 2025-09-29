package com.uphouse.monew.infrastructure.openapi.api;

import com.uphouse.monew.domain.article.dto.RssArticle;
import com.uphouse.monew.global.config.WebClientConfig;
import com.uphouse.monew.infrastructure.openapi.parser.RssParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverNewsApiClient {

    @Value("${external.naver.api.client-id}")
    private String clientId;

    @Value("${external.naver.api.client-secret}")
    private String clientSecret;

//    private static final String URL = "https://openapi.naver.com/v1/search/news.xml";
    private final @Qualifier("naverWebClient") WebClient naverWebClient;
    private final RssParser rssParser;

    public Mono<List<RssArticle>> fetchArticles(String query) {
        return naverWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/search/news.xml")
                        .queryParam("query", query)
                        .queryParam("display", 10)
                        .queryParam("start", 1)
                        .queryParam("sort", "sim")
                        .build())
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .onStatus(status -> status.value() == 429,
                        response -> Mono.error(new RuntimeException("429 Too Many Requests")))
                .bodyToMono(String.class)
                .delayElement(Duration.ofSeconds(1))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .maxBackoff(Duration.ofSeconds(30)))
                .map(rssParser::parse)
                .onErrorResume(e -> {
                    log.error("Failed to fetch NaverNewsRss RSS for query={}", query, e);
                    return Mono.just(Collections.emptyList());
                });
    }
}
