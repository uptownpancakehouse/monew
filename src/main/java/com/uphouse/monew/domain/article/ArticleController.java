package com.uphouse.monew.domain.article;

import com.uphouse.monew.domain.article.dto.ArticlePathParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/{articleId}/article-views")
    public ResponseEntity<String> createArticle(@PathVariable String articleId, @RequestBody UUID userId) {

        return ResponseEntity.ok("success");
    }

    @GetMapping
    public ResponseEntity<String> getArticleList(ArticlePathParams params) {

        return ResponseEntity.ok("success");
    }

    @GetMapping("/sources")
    public ResponseEntity<List<String>> getArticleSourceList() {
        List<String> response = articleService.getArticleSourceList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/restore")
    public ResponseEntity<String> articleRestore(ArticlePathParams params) {

        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<String> deleteArticle(@PathVariable String articleId) {
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/{articleId}/hard")
    public ResponseEntity<String> deleteHardArticle(@PathVariable String articleId) {

        return ResponseEntity.ok("success");
    }

}
