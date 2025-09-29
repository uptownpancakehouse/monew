package com.uphouse.monew.infrastructure.batch.writer;

import com.uphouse.monew.domain.article.domain.Article;
import com.uphouse.monew.domain.article.repository.ArticleRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

/**
 * Processor 에서 변환된 Article 엔티티 객체를 DB에 저장하는 Writer
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class RssItemWriter implements ItemWriter<Article> {

    private final ArticleRepository articleRepository;
    private EntityManager em;

    @Override
    public void write(Chunk<? extends Article> articles) {
        if (articles == null || articles.isEmpty()) {
            return;
        }

        log.info("Saving {} IndexInfo items to DB", articles.size());

        // JPA saveAll → 배치 insert
        articleRepository.saveAll(articles);
    }
}
