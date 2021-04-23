package org.learn.curd.repository.spec;

import org.learn.curd.model.Article;
import org.learn.curd.model.Tag;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public static Specification<Article> tag(String title,String description,String content,Set<String> tags) {
        return new Specification<Article>() {
            final List<Predicate> predicates = new ArrayList<>();
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Article, Tag> tagJoin = root.join("tags");

                if(!StringUtils.isEmpty(title)) {
                    predicates.add(criteriaBuilder.like(root.get("content"),"%"+title+"%"));
                }

                if (!StringUtils.isEmpty(description)) {
                    predicates.add(criteriaBuilder.like(root.get("description"),"%"+description+"%"));
                }

                if(!StringUtils.isEmpty(content)) {
                    predicates.add(criteriaBuilder.like(root.get("content"),"%"+content+"%"));
                }
                tags.forEach(tag -> {
                    predicates.add(criteriaBuilder.like(tagJoin.get("name"),"%"+tag+"%"));
                });
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
        };
    }

    public static Specification<Article> tag(Set<String> tags) {
        return new Specification<Article>() {
            final List<Predicate> predicates = new ArrayList<>();
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Article, Tag> tagJoin = root.join("tags");
                tags.forEach(tag -> {
                    predicates.add(criteriaBuilder.like(tagJoin.get("name"),"%"+tag+"%"));
                });

                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
        };
    }

}
