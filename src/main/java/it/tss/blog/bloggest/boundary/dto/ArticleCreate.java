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
public class ArticleCreate {
    @NotEmpty
    public String title;
    @NotEmpty
    public String textArticle;
}
