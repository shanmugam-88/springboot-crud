package org.learn.curd.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.learn.curd.model.audit.Auditable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ARTICLE")
public class Article extends Auditable {

    @Id
    //TODO: generation type identity is best (done)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO: add optional value for the one to one annotation
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    //TODO: examine the query generated and make changes if needed.
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    //TODO: column should be blob and restrict the size to 1 MB
    private String content;

    @ManyToMany
    @Cascade(CascadeType.PERSIST)
    @JoinTable(
            name = "article_tag",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
