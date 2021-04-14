/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.control;

import it.tss.blog.bloggest.boundary.dto.ArticleUpdate;
import it.tss.blog.bloggest.entity.Article;
import it.tss.blog.bloggest.entity.Tag;
import it.tss.blog.bloggest.control.TagStore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author utente
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class ArticleStore {

    @PersistenceContext
    private EntityManager em;

    /*@Inject
    @ConfigProperty(name = "maxResult", defaultValue = "15")
    int maxResult;*/
 /* @Inject
    CommentStore commentStore;*/
    @Inject
    TagStore tagStore;

    public Optional<Article> find(Long id) {
        Article found = em.find(Article.class, id);
        return found == null ? Optional.empty() : Optional.of(found);
    }

    public List<Article> searchByTitle(String title, Boolean visible) {
        return em.createQuery("select e from Article e where e.title= :title and e.visible=:visible order by e.title", Article.class)
                .setParameter("title", title == null ? "%" : "%" + title + "%")
                .setParameter("visible", visible)
                .getResultList();
    }
    
    public List<Article> findAll() {
        return em.createQuery("select e from Article e where e.visible=:true order by e.title", Article.class).getResultList();
    }

    public List<Article> searchByTag(List<String> tags) {
        List<Article> ris = new ArrayList<>();
        List<Article> all = new ArrayList<>();
        all = findAll();
        for(Article a : all){
            if(a.getTagNames().containsAll(tags))
                ris.add(a);
        }
        return ris;
    }

    public List<Article> searchByPeriod(LocalDateTime start, LocalDateTime finish) {
        return em.createQuery("select e from Article e where e.createdOn>= :start and e.createdOn<=:finish order by e.createdOn", Article.class)
                .getResultList();
    }

    public Article create(Article a) {
        return em.merge(a);
    }

    public Article update(Article article, ArticleUpdate a) {
        article.setTitle(a);
        article.setTextArticle(a);
        article.setVisible(a);
        return em.merge(article);
    }

}
