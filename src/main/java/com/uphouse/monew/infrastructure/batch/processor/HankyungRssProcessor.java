package com.uphouse.monew.infrastructure.batch.processor;

import com.uphouse.monew.domain.article.domain.Article;
import com.uphouse.monew.domain.article.dto.RssArticle;
import com.uphouse.monew.domain.article.dto.SourceType;
import com.uphouse.monew.domain.article.repository.ArticleRepository;
import com.uphouse.monew.infrastructure.openapi.api.NaverNewsApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * OpenAPI 에서 읽어온 RssArticle을 Article 엔티티로 변환하는 Processor
 * */

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class HankyungRssProcessor implements ItemProcessor<RssArticle, Article>, StepExecutionListener {

    private final NaverNewsApiClient naverNewsApiClient;
    private final ArticleRepository articleRepository;
    private final Set<String> hankyungArticleSet = new HashSet<>();

    @Override
    public void beforeStep(StepExecution stepExecution) {
        hankyungArticleSet.clear();
        List<Article> articles = articleRepository.findBySource(SourceType.HANKYUNG);
        articles.forEach(article -> hankyungArticleSet.add(article.getSourceUrl()));
        log.info("중복 체크 Set 초기화 완료, 현재 크기={}", hankyungArticleSet.size());
    }

    @Override
    public Article process(RssArticle article) throws Exception {

        if(hankyungArticleSet.contains(article.link())) {
            return null; // Writer로 넘어가지 않음
        }

        hankyungArticleSet.add(article.link());

        RssArticle naverArticles = Objects.requireNonNull(naverNewsApiClient.fetchArticles(article.title()).block())
                .stream().findFirst().orElse(article);


        return Article.builder()
                .sourceUrl(article.link())
                .title(article.title())
                .source(SourceType.HANKYUNG)
                .summary(naverArticles.description())
                .publishDate(article.publishedDate())
                .commentCount(0)
                .viewCount(0L)
                .build();
    }
}
