package org.learn.curd.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.learn.curd.dto.ArticleDTO;
import org.learn.curd.dto.ArticleResponseDTO;
import org.learn.curd.dto.SearchArticleDTO;
import org.learn.curd.dto.SearchResponse;
import org.learn.curd.logging.BaseLogger;
import org.learn.curd.logging.CustomLogFactory;
import org.learn.curd.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@Api("API to Load or Save the article")
public class ArticleController {

    private static final BaseLogger _logger = CustomLogFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleServiceImpl;

    @GetMapping("/{articleId}")
    @ApiOperation(value = "API to get an article")
    public ResponseEntity<ArticleResponseDTO> getArticle(@PathVariable Long articleId) {
        ArticleResponseDTO articleResponseDTO = articleServiceImpl.getArticle(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(articleResponseDTO);
    }

    @PostMapping
    @PreAuthorize("@apiPreAuthCheck.hasPermission('CREATE_ARTICLE','UPDATE_ARTICLE','DELETE_ARTICLE')")
    @ApiOperation(value = "API to post an article")
    public ResponseEntity<ArticleResponseDTO> postArticle(@RequestBody ArticleDTO articleDTO) {
        ArticleResponseDTO articleResponseDTO = articleServiceImpl.postArticle(articleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDTO);
    }

    @PostMapping("/search")
    @ApiOperation(value = "API to search an article")
    public ResponseEntity<SearchResponse> searchArticle(@RequestBody SearchArticleDTO searchArticleDTO,
                                                        @RequestParam(defaultValue = "0") int offset , @RequestParam(defaultValue = "1") int limit) {
        SearchResponse response = articleServiceImpl.searchArticle(searchArticleDTO,offset,limit);
        if(response.getArticles()==null || response.getArticles().isEmpty()) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{articleId}")
    @PreAuthorize("@apiPreAuthCheck.hasPermission({'DELETE_PRIVILEGE'})")
    @ApiOperation(value = "API to delete an article")
    public ResponseEntity<ArticleResponseDTO> deleteArticle(@PathVariable Long articleId) {
        articleServiceImpl.deleteArticle(articleId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
