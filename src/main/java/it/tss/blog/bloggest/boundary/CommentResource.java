/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.boundary;


import it.tss.blog.bloggest.control.ArticleStore;
import it.tss.blog.bloggest.control.CommentStore;
import it.tss.blog.bloggest.entity.Comment;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author utente
 */
//@SecurityRequirement(name = "jwt")
@RolesAllowed({"ADMIN", "USER"})
public class CommentResource {

    @Inject
    private ArticleStore articleStore;

    @Inject
    private CommentStore commentStore;

    private Long commentId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Comment find() {
        Comment comment = commentStore.find(commentId).orElseThrow(() -> new NotFoundException());
        return comment;
    }

    @RolesAllowed({"ADMIN"})
    @PATCH
    public Response hideComment() {
        commentStore.find(commentId).orElseThrow(() -> new NotFoundException());
        commentStore.hide(commentId);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> allAnswersToComment() {
        return commentStore.searchByRefComm(commentId);
    }
    
    

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
    
    
}
