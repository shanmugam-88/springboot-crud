package org.learn.curd.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.learn.curd.dto.ArticleDTO;
import org.learn.curd.dto.ArticleResponseDTO;
import org.learn.curd.dto.SearchArticleDTO;
import org.learn.curd.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@Api("API to Load or Save the article")
public class ArticleController {

    @Autowired
    private ArticleService articleServiceImpl;

    @GetMapping("/{articleId}")
    @ApiOperation(value = "API to get an article")
    public ResponseEntity<ArticleResponseDTO> getArticle(@PathVariable Long articleId) {
        ArticleResponseDTO articleResponseDTO = articleServiceImpl.getArticle(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(articleResponseDTO);
    }

    @PostMapping
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
    @ApiOperation(value = "API to delete an article")
    public ResponseEntity<ArticleResponseDTO> deleteArticle(@PathVariable Long articleId) {
        articleServiceImpl.deleteArticle(articleId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
