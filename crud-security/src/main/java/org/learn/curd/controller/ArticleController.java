package org.learn.curd.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.learn.curd.dto.ArticleDTO;
import org.learn.curd.dto.ArticleResponseDTO;
import org.learn.curd.dto.SearchArticleDTO;
import org.learn.curd.logging.BaseLogger;
import org.learn.curd.logging.CustomLogFactory;
import org.learn.curd.service.ArticleService;
import org.learn.curd.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<ArticleResponseDTO>> searchArticle(@RequestBody SearchArticleDTO searchArticleDTO) {
        List<ArticleResponseDTO> response = articleServiceImpl.searchArticle(searchArticleDTO);
        if(response.isEmpty()) {
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
