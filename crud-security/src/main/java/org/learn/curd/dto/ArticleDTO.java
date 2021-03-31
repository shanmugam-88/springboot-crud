package org.learn.curd.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import java.util.Set;
@ApiOperation(value = "Create article data object")
public class ArticleDTO {
    @ApiModelProperty(
            allowableValues = "Title of the article",
            required = true
    )
    private String title;
    @ApiModelProperty(
            allowableValues = "Short description about article",
            required = true
    )
    private String description;
    @ApiModelProperty(
            allowableValues = "Content of the article",
            required = true
    )
    private String content;

    @ApiModelProperty(
            allowableValues = "['Chennai','India']",
            required = false
    )
    private Set<String> tags;

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

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
