/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.entity;


import it.tss.blog.bloggest.boundary.dto.UserCreate;
import it.tss.blog.bloggest.boundary.dto.UserUpdate;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author utente
 */

@NamedQueries({
    @NamedQuery(name = User.LOGIN, query = "select e from User e where e.email=:email and e.pwd=:pwd and e.deleted=false")
})

@Entity
@Table(name = "user")
public class User extends AbstractEntity implements Serializable {

    public enum Role {
        ADMIN, USER
    }

    public static final String LOGIN = "User.login";
    
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "user_sequence")
    protected Long id;

    private String nome;

    private String cognome;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String pwd;
    
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private boolean blocked = false;

    private boolean deleted = false;

    public User() {
    }

    public User(UserCreate u) {
        this.nome = u.nome;
        this.cognome = u.cognome;
        this.email = u.email;
        this.pwd = u.pwd;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNome(UserUpdate u) {
        setCognome(u.nome == null ? this.nome : u.nome);
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setCognome(UserUpdate u) {
        setCognome(u.cognome == null ? this.cognome : u.cognome);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setPwd(UserUpdate u) {
        setCognome(u.pwd == null ? this.pwd : u.pwd);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final User other = (User) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }


}
