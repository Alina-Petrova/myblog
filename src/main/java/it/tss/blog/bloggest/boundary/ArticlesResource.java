/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.boundary;

import it.tss.blog.bloggest.boundary.dto.ArticleCreate;
import it.tss.blog.bloggest.control.ArticleStore;
import it.tss.blog.bloggest.entity.Article;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author utente
 */
@RolesAllowed({"ADMIN"})
@Path("/articles")
public class ArticlesResource {

    @Context
    private ResourceContext resource;

    @Context
    private UriInfo uriInfo;

    @PostConstruct
    public void init() {
        System.out.println(uriInfo.getPath());
        System.out.println(uriInfo.getBaseUri());
        System.out.println(uriInfo.getAbsolutePath());
    }

    @Inject
    ArticleStore articleStore;

    @RolesAllowed({"ADMIN"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createArticle(ArticleCreate a, List<String> tags) {
        Article created = articleStore.create(new Article(a, tags));
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> allArticles() {
        return resource.getResource(ArticleResource.class).allArticles();
    }
    
    
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> allArticlesByTag(List<String> tags) {
        return articleStore.searchByTag(tags);
    }

    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> allArticlesByPeriod(LocalDateTime start, LocalDateTime finish) {
        return articleStore.searchByPeriod(start, finish);
    }

    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> allArticlesByTitle(String title, Boolean visible) {
        return articleStore.searchByTitle(title, visible);
    }        
    
        
    @RolesAllowed({"ADMIN", "USER"})
    @Path("{articleId}")
    public ArticleResource search(@PathParam("articleId") Long id) {
        ArticleResource sub = resource.getResource(ArticleResource.class);
        sub.setArticleId(id);
        return sub;
    }

}
