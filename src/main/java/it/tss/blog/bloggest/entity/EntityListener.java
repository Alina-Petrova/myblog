/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.entity;


import it.tss.blog.bloggest.control.UserStore;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author utente
 */
public class EntityListener {

    @Inject
    UserStore userStore;

    @Inject
    JsonWebToken jwt;

    @PostConstruct
    public void init() {
        System.out.println("init entity listener..");
    }

    @PrePersist
    public void onPrePersist(AbstractEntity e) {
        if (jwt != null && jwt.getSubject() != null) {
            e.setCreatedBy(userStore.find(Long.parseLong(jwt.getSubject())).get());
        }
        e.setCreatedOn(LocalDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate(AbstractEntity e) {
        if (jwt != null && jwt.getSubject() != null) {
            e.setCreatedBy(userStore.find(Long.parseLong(jwt.getSubject())).get());
        }
        e.setModifiedOn(LocalDateTime.now());
    }
}
