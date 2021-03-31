package org.learn.curd.repository.spec;

import org.learn.curd.model.Article;
import org.springframework.data.jpa.domain.Specification;

public class ArticleSpecification {

    public static Specification<Article> titleContains(String title){
        return ((root, criteriaQuery, criteriaBuilder) -> {
            if (title==null) {
                return criteriaBuilder.conjunction();
            }
            return  criteriaBuilder.like(root.get("title"),"%"+title+"%");
        });

    }

    public static Specification<Article> descriptionContains(String description){
        return ((root, criteriaQuery, criteriaBuilder) -> {
            if(description==null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("description"),"%"+description+"%");
        });
    }

    public static Specification<Article> contentContains(String content){
        return ((root, criteriaQuery, criteriaBuilder) -> {
            if(content==null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("content"),"%"+content+"%");
        });

    }

}
