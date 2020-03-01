package com.wgmao.article.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.wgmao.article.pojo.Article;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zyxyz zhongyue314@163.com
 * @date: 2020/1/11
 */
public interface ArticleService {
    List<Article> findAll();

    Article findById(String articleId);

    void save(Article article);

    void updateById(Article article);

    void deleteById(String articleId);

    Page<Article> findByPage(Map<String, Object> map, Integer page, Integer size);

    List<Article> list();

    void thumbup(String articleId, String userId);

    Boolean subscribe(String articleId, String userId);
}
