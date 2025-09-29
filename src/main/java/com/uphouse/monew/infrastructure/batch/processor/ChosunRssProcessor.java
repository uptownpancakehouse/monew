package com.uphouse.monew.infrastructure.batch.processor;

import com.uphouse.monew.domain.article.domain.Article;
import com.uphouse.monew.domain.article.dto.RssArticle;
import com.uphouse.monew.domain.article.dto.SourceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class ChosunRssProcessor implements ItemProcessor<RssArticle, Article>, StepExecutionListener {

    @Override
    public Article process(RssArticle article) throws Exception {

        return Article.builder()
                .sourceUrl(article.link())
                .title(article.title())
                .source(SourceType.CHOSUN)
                .summary(article.description())
                .publishDate(article.publishedDate())
                .commentCount(0)
                .viewCount(0L)
                .build();
    }
}
