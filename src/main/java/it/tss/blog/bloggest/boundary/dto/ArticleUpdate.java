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

public class ArticleUpdate {
    public String title;
    public String textArticle;
    public Boolean visible;

    public ArticleUpdate() {
    }

    public ArticleUpdate(String title, String textArticle, boolean visible) {
        this.title = title;
        this.textArticle = textArticle;
        this.visible = visible;
    }
}
