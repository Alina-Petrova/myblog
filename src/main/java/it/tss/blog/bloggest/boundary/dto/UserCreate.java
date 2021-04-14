/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.boundary.dto;

import javax.validation.constraints.NotEmpty;

/**
 *
 * @author utente
 */
public class UserCreate {
    
    public String nome;
    @NotEmpty
    public String cognome;
    @NotEmpty
    public String email;
    @NotEmpty
    public String pwd;  
}
