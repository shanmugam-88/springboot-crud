package org.learn.curd.service;

import org.learn.curd.dto.ArticleDTO;
import org.learn.curd.dto.ArticleResponseDTO;
import org.learn.curd.dto.SearchArticleDTO;

import java.util.List;

public interface ArticleService {
    ArticleResponseDTO postArticle(ArticleDTO articleRequest);
    ArticleResponseDTO getArticle(Long articleId);
    List<ArticleResponseDTO> searchArticle(SearchArticleDTO searchArticleDTO);
    void deleteArticle(Long articleId);
}
