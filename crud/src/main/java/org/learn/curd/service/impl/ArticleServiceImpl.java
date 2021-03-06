package org.learn.curd.service.impl;

import org.learn.curd.dto.ArticleDTO;
import org.learn.curd.dto.ArticleResponseDTO;
import org.learn.curd.dto.SearchArticleDTO;
import org.learn.curd.exception.BusinessException;
import org.learn.curd.exception.StatusCode;
import org.learn.curd.logging.BaseLogger;
import org.learn.curd.logging.CustomLogFactory;
import org.learn.curd.logging.LogLevel;
import org.learn.curd.model.Article;
import org.learn.curd.model.Tag;
import org.learn.curd.model.User;
import org.learn.curd.repository.ArticleRepository;
import org.learn.curd.repository.AuthorRepository;
import org.learn.curd.repository.TagRepository;
import org.learn.curd.repository.spec.ArticleSpecification;
import org.learn.curd.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final BaseLogger _logger = CustomLogFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

    //check propogation
    //TODO: diff b/w spring vs javax transaction
    @Override
    public ArticleResponseDTO postArticle(ArticleDTO articleRequest) {
        _logger.log(LogLevel.DEBUG,"Post article method is invoked");
        Optional<User> user = authorRepository.findById(articleRequest.getUserId());
        if(user.isEmpty()) {
            _logger.log(LogLevel.ERROR,"Unable to load the user for given article");
            throw new BusinessException(StatusCode.ERR_USR_0001);
        }
        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setContent(articleRequest.getContent());
        article.setDescription(articleRequest.getDescription());
        for (String tagName : articleRequest.getTags()) {
            Tag tag = tagRepository.findByName(tagName);
            if (tag == null) {
                tag = new Tag();
                tag.setName(tagName);
                article.getTags().add(tag);
            } else {
                article.getTags().add(tag);
            }
        }
        article.setUser(user.get());
        article = articleRepository.save(article);
        _logger.log(LogLevel.DEBUG,"Article created with id " + article.getId());
        return modelMapper.map(article,ArticleResponseDTO.class);
    }

    @Override
    public ArticleResponseDTO getArticle(Long articleId) {
        _logger.log(LogLevel.DEBUG,"Get article invoked " + articleId);
        Optional<Article> article = articleRepository.findById(articleId);
        if(article.isEmpty()) {
            _logger.log(LogLevel.ERROR,"Article does n't exist " + articleId);
            throw new BusinessException(StatusCode.ERR_ART_0001);
        }
        return modelMapper.map(article.get(),ArticleResponseDTO.class);
    }

    @Override
    public List<ArticleResponseDTO> searchArticle(SearchArticleDTO searchArticle) {
        List<ArticleResponseDTO> responseDTOS = new ArrayList<>();
        List<Article> articleList = articleRepository.findAll(
                        Specification.where(ArticleSpecification.titleContains(searchArticle.getTitle()).
                                and(ArticleSpecification.descriptionContains(searchArticle.getDescription())).
                                and(ArticleSpecification.contentContains(searchArticle.getContent()))));
        articleList.forEach(article -> {
            responseDTOS.add(modelMapper.map(article,ArticleResponseDTO.class));
        });
        return responseDTOS;
    }

    @Override
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
