/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author utente
 */
@Entity
@Table(name = "comment")
public class Comment extends AbstractEntity implements Serializable{
    
    @Id
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(generator = "comment_sequence")
    private Long id; 
    
    public enum Valutazione {
        BRUTTO, PASSABILE, SODDISFACENTE, BUONO, OTTIMO
    }
    
    @Column(nullable = false)
    private String textComment;
    @Column(nullable = false)
    private Valutazione valutazione;
    @ManyToOne(optional = false)
    private Article article;
    @ManyToOne(optional = false)
    private User user;
    @ManyToOne
    private Comment commentRef;
    private Boolean visible = true;

    public Comment() {
    }

    public Comment(String textComment, Valutazione valutazione) {
        this.textComment = textComment;
        this.valutazione = valutazione;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextComment() {
        return textComment;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    public Valutazione getValutazione() {
        return valutazione;
    }

    public void setValutazione(Valutazione valutazione) {
        this.valutazione = valutazione;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getCommentRef() {
        return commentRef;
    }

    public void setCommentRef(Comment commentRef) {
        this.commentRef = commentRef;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Comment other = (Comment) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
    
    
}
