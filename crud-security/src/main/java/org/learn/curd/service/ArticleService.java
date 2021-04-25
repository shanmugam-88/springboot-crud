package org.learn.curd.service;

import org.learn.curd.dto.ArticleDTO;
import org.learn.curd.dto.ArticleResponseDTO;
import org.learn.curd.dto.SearchArticleDTO;
import org.learn.curd.dto.SearchResponse;

public interface ArticleService {
    ArticleResponseDTO postArticle(ArticleDTO articleRequest);
    ArticleResponseDTO getArticle(Long articleId);
    SearchResponse searchArticle(SearchArticleDTO searchArticleDTO, int offSet, int limit);
    void deleteArticle(Long articleId);
}
