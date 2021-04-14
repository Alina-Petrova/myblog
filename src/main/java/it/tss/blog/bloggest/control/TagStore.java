/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.control;


import it.tss.blog.bloggest.entity.Tag;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author utente
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class TagStore {

    @PersistenceContext
    private EntityManager em;

    public Optional<Tag> find(Long id) {
        Tag found = em.find(Tag.class, id);
        return found == null ? Optional.empty() : Optional.of(found);
    }

    public List<Tag> search(String tagName) {
        return em.createQuery("select e from Tag e where e.tag_name=:tagName", Tag.class).getResultList();
    }

    public Tag create(Tag t) {
        
        return em.merge(t);
    }
}
