package com.uphouse.monew.domain.article;

import com.uphouse.monew.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<String> getArticleSourceList() {
        return articleRepository.findSourcesList();
    }
}
