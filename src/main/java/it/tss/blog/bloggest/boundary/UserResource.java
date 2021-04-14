/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.boundary;


import it.tss.blog.bloggest.boundary.dto.UserUpdate;
import it.tss.blog.bloggest.control.ArticleStore;
import it.tss.blog.bloggest.control.UserStore;
import it.tss.blog.bloggest.entity.Article;
import it.tss.blog.bloggest.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import javax.xml.stream.events.Comment;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

/**
 *
 * @author utente
 */
@SecurityRequirement(name = "jwt")
@RolesAllowed({"ADMIN", "USER"})
public class UserResource {

    @Inject
    private ArticleStore articleStore;

   /* @Inject
    private CommentStore commentStore;*/

    @Inject
    private UserStore userStore;

    private Long userId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User find() {
        User user = userStore.find(userId).orElseThrow(() -> new NotFoundException());
        return user;
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User update(UserUpdate u) {
        User user = userStore.find(userId).orElseThrow(() -> new NotFoundException());
        User updated = userStore.update(user, u);
        return updated;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete() {
        User user = userStore.find(userId).orElseThrow(() -> new NotFoundException());
        userStore.delete(userId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @RolesAllowed({"ADMIN"})
    @PATCH
    public Response blockUser() {
        userStore.find(userId).orElseThrow(() -> new NotFoundException());
        userStore.block(userId);
        return Response.status(Response.Status.ACCEPTED).build();
    }

  /*  @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> allCommentsByArticle(Long idArticle) {
        return commentStore.searchByArticle(idArticle);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> allUsersCommentsByArticle(Long idArticle) {
        return commentStore.searchByUserAndArticle(idArticle, userId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> allUsersComments() {
        return commentStore.searchByUser(userId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> allCommentsByPeriod(LocalDateTime start, LocalDateTime finish) {
        return commentStore.searchByPeriod(start, finish);
    }*/

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> allArticlesByPeriod(LocalDateTime start, LocalDateTime finish) {
        return articleStore.searchByPeriod(start, finish);
    }

    @RolesAllowed({"ADMIN"})
    @POST
    @Path("/articles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createArticle(Article article) {
        User user = userStore.find(userId).orElseThrow(() -> new NotFoundException());
        Article saved = articleStore.create(article);
        return Response.status(Response.Status.CREATED)
                .entity(saved)
                .build();
    }

/*    @POST
    @Path("comments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment(Comment comment, Long articleId) {
        User user = userStore.find(userId).orElseThrow(() -> new NotFoundException());
        Comment saved = commentStore.create(comment, articleId, userId);
        saved.setUser(userStore.find(userId).get());
        return Response.status(Response.Status.CREATED)
                .entity(saved)
                .build();
    }*/

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
