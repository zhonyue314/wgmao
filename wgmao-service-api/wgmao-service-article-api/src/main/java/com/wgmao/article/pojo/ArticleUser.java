package com.wgmao.article.pojo;

import java.io.Serializable;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/2/28 0:18
 */
public class ArticleUser implements Serializable {
    private String username;
    private Article article;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
