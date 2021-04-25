package org.learn.curd.service.impl;

import org.learn.curd.dto.ArticleDTO;
import org.learn.curd.dto.ArticleResponseDTO;
import org.learn.curd.dto.ResponsePageable;
import org.learn.curd.dto.SearchArticleDTO;
import org.learn.curd.dto.SearchResponse;
import org.learn.curd.exception.BusinessException;
import org.learn.curd.exception.StatusCode;
import org.learn.curd.logging.BaseLogger;
import org.learn.curd.logging.CustomLogFactory;
import org.learn.curd.logging.LogLevel;
import org.learn.curd.model.Article;
import org.learn.curd.model.Tag;
import org.learn.curd.model.User;
import org.learn.curd.repository.ArticleRepository;
import org.learn.curd.repository.UserRepository;
import org.learn.curd.repository.TagRepository;
import org.learn.curd.repository.spec.ArticleSpecification;
import org.learn.curd.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final BaseLogger _logger = CustomLogFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

    //check propogation
    //TODO: diff b/w spring vs javax transaction
    @Override
    public ArticleResponseDTO postArticle(ArticleDTO articleRequest) {
        _logger.log(LogLevel.DEBUG,"Post article method is invoked");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;
        if(!(principal instanceof UserDetails)) {
            _logger.log(LogLevel.ERROR,"Unable to load the user for given article");
            throw new BusinessException(StatusCode.ERR_USR_0001);
        }
        user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        article.setUser(user);
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
    public SearchResponse searchArticle(SearchArticleDTO searchArticle,int offSet,int limit) {
        List<ArticleResponseDTO> responseDTOS = new ArrayList<>();
        Pageable pageable = PageRequest.of(offSet,limit);
        Page<Article> articleList = articleRepository.findAll(
                Specification.where(
                        ArticleSpecification.tag(
                                searchArticle.getTitle(),
                                searchArticle.getDescription(),
                                searchArticle.getContent(),
                                searchArticle.getTag()
                        )
                )
        ,pageable);
        if(articleList.isEmpty()) {
            _logger.log(LogLevel.ERROR,"No record found");
            throw new BusinessException(StatusCode.ERR_0001,"No record found");
        }
        articleList.getContent().forEach(article -> {
            responseDTOS.add(modelMapper.map(article,ArticleResponseDTO.class));
        });
        ResponsePageable responsePageable = new ResponsePageable(articleList.getTotalElements(), offSet,limit);
        return new SearchResponse(responseDTOS,responsePageable);
    }

    @Override
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
