/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.entity;


import it.tss.blog.bloggest.boundary.dto.ArticleCreate;
import it.tss.blog.bloggest.boundary.dto.ArticleUpdate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author utente
 */
@Entity
@Table(name = "article")
public class Article extends AbstractEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "article_sequence", sequenceName = "article_sequence", initialValue = 100, allocationSize = 1)
    @GeneratedValue(generator = "article_sequence")
    protected Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String textArticle;
    private Boolean visible = true;
    
    @ManyToMany
    @JoinTable(name = "article_tag", joinColumns = @JoinColumn(name = "article_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> articleTags = new HashSet<>();

    public Article() {
    }

    public Article(ArticleCreate a, List<String> tags) {
        this.title = a.title;
        this.textArticle = a.textArticle;
        for(String s : tags)
            articleTags.add(new Tag(s));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Tag> getArticleTags() {
        return articleTags;
    }
    
    public List<String> getTagNames() {
        List<String> tagNames = new ArrayList<>();
        Set <Tag> tags = getArticleTags();
        for(Tag t : tags)
            tagNames.add(t.getTag_name());
        return tagNames;
    }

    public void setArticleTags(Set<Tag> articleTags) {
        this.articleTags = articleTags;
    }    
    
    public void setTitle(ArticleUpdate a) {
        this.title = a.title == null ? this.title : a.title;
    }

    public String getTextArticle() {
        return textArticle;
    }

    public void setTextArticle(String textArticle) {
        this.textArticle = textArticle;
    }

    public void setTextArticle(ArticleUpdate a) {
        this.textArticle = a.textArticle == null ? this.textArticle : a.textArticle;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public void setVisible(ArticleUpdate a) {
        this.visible = a.visible == null ? this.visible : a.visible;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Article other = (Article) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
