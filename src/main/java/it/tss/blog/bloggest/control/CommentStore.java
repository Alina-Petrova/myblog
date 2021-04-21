/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.control;


import it.tss.blog.bloggest.entity.Article;
import it.tss.blog.bloggest.entity.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author utente
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class CommentStore {

    @PersistenceContext
    private EntityManager em;

    @Inject
    @ConfigProperty(name = "maxResult", defaultValue = "15")
    int maxResult;

    @Inject
    UserStore userStore;

    @Inject
    ArticleStore articleStore;

    public Optional<Comment> find(Long id) {
        Comment found = em.find(Comment.class, id);
        return found == null ? Optional.empty() : Optional.of(found);
    }

    private TypedQuery<Comment> searchQuery(boolean visible, Long userId, Long articleId, Long commentId, Long commentRefId, LocalDateTime begin, LocalDateTime end) {
        return em.createQuery("select e from Comment e where e.visible= :visible and e.userId= :userId and e.articleId= :articleId"
                + " and e.id= :commentId and e.commentRefId= :commentRefId and e.createdOn >= begin and e.createdOn <= end order by e.id ", Comment.class)
                .setParameter("visible", visible)
                .setParameter("userId", userId == null ? "%" : userId)
                .setParameter("articleId", articleId == null ? "%" : articleId)
                .setParameter("id", commentId == null ? "%" : commentId)
                .setParameter("commentRefId", commentRefId == null ? "%" : commentRefId)
                .setParameter("from", begin == null ? "%" : begin)
                .setParameter("to", end == null ? "%" : end);
    }

    public List<Comment> searchByArticle(Long articleId) {
        return searchQuery(true, null, articleId, null, null, null, null).getResultList();
    }

    public List<Comment> searchByPeriod(LocalDateTime start, LocalDateTime finish) {
        return searchQuery(true, null, null, null, null, start, finish).getResultList();
    }

    public List<Comment> searchByRefComm(Long commentRefId) {
        return searchQuery(true, null, null, null, commentRefId, null, null).getResultList();
    }

    public List<Comment> searchByUser(Long userId) {
        return searchQuery(true, userId, null, null, null, null, null).getResultList();
    }

    public List<Comment> searchByUserAndArticle(Long userId, Long articleId) {
        return searchQuery(true, userId, articleId, null, null, null, null).getResultList();
    }

    public Comment create(Comment c, Long userId, Article article, Comment commentRef) {
        c.setUser(userStore.find(userId).get());
        if(article!=null)
            c.setArticle(article);
        else
            c.setCommentRef(commentRef);
        return em.merge(c);
    }

    public void hide(Long id) {
        Comment found = em.find(Comment.class, id);
        found.setVisible(false);
        em.merge(found);
    }

}
