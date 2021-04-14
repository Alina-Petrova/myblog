/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.boundary.dto;

/**
 *
 * @author utente
 */
public class UserUpdate {
    
    public String nome;
    public String cognome;
    public String pwd;


    public UserUpdate() {
    }

    public UserUpdate(String nome, String cognome, String pwd) {
        this.nome = nome;
        this.cognome = cognome;
        this.pwd = pwd;
    }

    
}
