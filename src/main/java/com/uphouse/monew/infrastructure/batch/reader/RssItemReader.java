package com.uphouse.monew.infrastructure.batch.reader;

import com.uphouse.monew.domain.article.dto.RssArticle;
import com.uphouse.monew.infrastructure.openapi.rss.HankyungRssClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RssItemReader implements ItemReader<RssArticle> {

    private final HankyungRssClient hankyungRssClient;
    private Iterator<RssArticle> iterator;

    @Override
    public RssArticle read() throws Exception {
        if (iterator == null || !iterator.hasNext()) {
            List<RssArticle> articleList = hankyungRssClient.fetchArticles();



            if(articleList == null || articleList.isEmpty()) {
                return null;
            }

            this.iterator = articleList.iterator();
        }
        return iterator.hasNext() ? iterator.next() : null;
    }
}
