package org.learn.curd.dto;

import java.util.Set;

public class ArticleResponseDTO {

    private Long articleId;

    //private UserResponseDTO user;

    private String title;

    private String description;

    private String content;

    private Set<TagResponseDTO> tags;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    /*public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<TagResponseDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagResponseDTO> tags) {
        this.tags = tags;
    }
}
