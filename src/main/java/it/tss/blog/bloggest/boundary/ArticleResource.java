/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.boundary;

import it.tss.blog.bloggest.boundary.dto.ArticleUpdate;
import it.tss.blog.bloggest.control.ArticleStore;
import it.tss.blog.bloggest.entity.Article;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author utente
 */
@RolesAllowed({"ADMIN"})
public class ArticleResource {

    @Inject
    private ArticleStore articleStore;

    /* @Inject
    private CommentStore commentStore;*/
    private Long articleId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Article find() {
        Article article = articleStore.find(articleId).orElseThrow(() -> new NotFoundException());
        return article;
    }

    // @RolesAllowed({"ADMIN"})
    @PATCH
    @Path("articles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Article updateArticle(ArticleUpdate articleUpdate) {
        Article found = articleStore.find(articleId).orElseThrow(() -> new NotFoundException());
        Article updated = articleStore.update(found, articleUpdate);
        return updated;
    }

    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> allArticlesByTag(List<String> tags) {
        return articleStore.searchByTag(tags);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> allArticlesByPeriod(LocalDateTime start, LocalDateTime finish) {
        return articleStore.searchByPeriod(start, finish);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> allByTitle(String title, Boolean visible) {
        return articleStore.searchByTitle(title, visible);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> allArticles() {
        return articleStore.findAll();
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}
