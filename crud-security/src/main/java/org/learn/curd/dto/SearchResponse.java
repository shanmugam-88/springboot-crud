package org.learn.curd.dto;

import java.util.List;

public class SearchResponse {
    private List<ArticleResponseDTO> articles;
    private ResponsePageable pageable;

    public SearchResponse(List<ArticleResponseDTO> articles, ResponsePageable pageable) {
        this.articles = articles;
        this.pageable = pageable;
    }

    public List<ArticleResponseDTO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleResponseDTO> articles) {
        this.articles = articles;
    }

    public ResponsePageable getPageable() {
        return pageable;
    }

    public void setPageable(ResponsePageable pageable) {
        this.pageable = pageable;
    }
}
