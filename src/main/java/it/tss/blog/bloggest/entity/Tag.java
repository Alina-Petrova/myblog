/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author utente
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    @Id
    @SequenceGenerator(name = "tag_sequence", sequenceName = "tag_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "tag_sequence")
    private Long id;

    @Column(nullable = false)
    private String tag_name;

    @ManyToMany(mappedBy = "articleTags")
    private Set<Article> articlesTaged = new HashSet<>();

    public Tag() {
    }

    public Tag(String tag_name) {
        this.tag_name = tag_name;
    }

    public Set<Article> getArticlesTaged() {
        return articlesTaged;
    }

    public void setArticlesTaged(Set<Article> articlesTaged) {
        this.articlesTaged = articlesTaged;
    }
    
    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Tag other = (Tag) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
