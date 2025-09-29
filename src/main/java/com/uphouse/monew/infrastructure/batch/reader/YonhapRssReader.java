package com.uphouse.monew.infrastructure.batch.reader;

import com.uphouse.monew.domain.article.dto.RssArticle;
import com.uphouse.monew.infrastructure.openapi.rss.YonhapRssClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class YonhapRssReader implements ItemReader<RssArticle> {

    private final YonhapRssClient yonhapRssClient;
    private Iterator<RssArticle> item;

    @Override
    public RssArticle read() throws Exception {
        if(item == null) {
            List<RssArticle> articleList = yonhapRssClient.fetchArticles();

            if (articleList == null || articleList.isEmpty()) {
                return null;
            }

            this.item = articleList.iterator();
        }
        return item.hasNext() ? item.next() : null;
    }
}
