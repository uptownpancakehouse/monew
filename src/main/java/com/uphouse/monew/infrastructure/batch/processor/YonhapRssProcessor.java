package com.uphouse.monew.infrastructure.batch.processor;

import com.uphouse.monew.domain.article.domain.Article;
import com.uphouse.monew.domain.article.dto.RssArticle;
import com.uphouse.monew.domain.article.dto.SourceType;
import com.uphouse.monew.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class YonhapRssProcessor implements ItemProcessor<RssArticle, Article>, StepExecutionListener {

    private final ArticleRepository articleRepository;
    private final Set<String> yonhapArticleSet = new HashSet<>();

    @Override
    public void beforeStep(StepExecution stepExecution) {
        yonhapArticleSet.clear();
        List<Article> articles = articleRepository.findBySource(SourceType.YONHAP);
        articles.forEach(article -> yonhapArticleSet.add(article.getSourceUrl()));
        log.info("중복 체크 Set 초기화 완료, 현재 크기={}", yonhapArticleSet.size());
    }

    @Override
    public Article process(RssArticle article) throws Exception {

        if(yonhapArticleSet.contains(article.link())) return null;

        yonhapArticleSet.add(article.link());

        return Article.builder()
                .sourceUrl(article.link())
                .title(article.title())
                .source(SourceType.YONHAP)
                .summary(article.description())
                .publishDate(article.publishedDate())
                .commentCount(0)
                .viewCount(0L)
                .build();
    }
}
