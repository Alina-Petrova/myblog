/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.control;

import it.tss.blog.bloggest.boundary.dto.UserUpdate;
import it.tss.blog.bloggest.entity.User;
import it.tss.blog.security.control.SecurityEncoding;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
public class UserStore {

    @PersistenceContext
    private EntityManager em;
    
    private System.Logger LOG = System.getLogger(UserStore.class.getName());     //----------------------
    

    @Inject
    @ConfigProperty(name = "maxResult", defaultValue = "15")
    int maxResult;

    public Optional<User> find(Long id) {
        LOG.log(System.Logger.Level.INFO, "search user " + id);                  //-------------------
        User found = em.find(User.class, id);
        return found == null ? Optional.empty() : Optional.of(found);
    }
    
    public Optional<User> findByEmailAndPwd(String email, String pwd) {
        try {
            User found = em.createNamedQuery(User.LOGIN, User.class)    //em.createQuery("select e from User e where e.email=: email and e.pwd=: pwd", User.class)
                    .setParameter("email", email)
                    .setParameter("pwd", SecurityEncoding.shaHash(pwd))
                    .getSingleResult();
            return Optional.of(found);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    /*----searches----------------*/
    private TypedQuery<User> searchQuery(boolean blocked, String cognome) {
        return em.createQuery("select e from User e where e.blocked=:blocked and e.cognome like :cognome order by e.cognome ", User.class)
                .setParameter("blocked", blocked)
                .setParameter("cognome", cognome == null ? "%" : cognome);
    }

    public List<User> searchAll() {
        return searchQuery(false, null).getResultList();
    }

    public List<User> search(int start, boolean blocked, String cognome) {
        return searchQuery(blocked, cognome)
                .setFirstResult(start)
                .setMaxResults(maxResult == 0 ? this.maxResult : maxResult)
                .getResultList();
    }

    public User create(User u) {
        u.setPwd(SecurityEncoding.shaHash(u.getPwd()));
        User saved = em.merge(u);
        return saved;
    }

    public User update(User user, UserUpdate u) {
        user.setNome(u);
        user.setCognome(u);
        user.setPwd(u);        
        return em.merge(user);
    }

    public void block(Long id) {
        User found = em.find(User.class, id);
        found.setBlocked(true);
        em.merge(found);
    }

    public void delete(Long id) {
        User found = em.find(User.class, id);
        found.setDeleted(true);
        em.merge(found);
        
    }

}
