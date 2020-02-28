package com.wgmao.article.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wgmao.article.dao.ArticleMapper;
import com.wgmao.article.pojo.Article;
import com.wgmao.article.service.ArticleService;
import com.wgmao.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:
 * @author: zyxyz zhongyue314@163.com
 * @date: 2020/1/11
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private IdWorker idWorker;

    @Override
    public List<Article> findAll() {
        List<Article> articleList = articleMapper.selectList(null);
        return articleList;
    }

    @Override
    public Article findById(String articleId) {
        Article article = articleMapper.selectById(articleId);
        return article;
    }

    @Override
    public void save(Article article) {
        String id = idWorker.nextId() + "";
        article.setId(id);
        //初始化数据
        article.setVisits(0);
        article.setThumbup(0);
        article.setComment(0);
        articleMapper.insert(article);
    }

    @Override
    public void updateById(Article article) {
        articleMapper.updateById(article);
    }

    @Override
    public void deleteById(String articleId) {
        articleMapper.deleteById(articleId);
    }

    public Page<Article> findByPage(Map<String, Object> map, Integer page, Integer size) {
        //设置条件查询
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            wrapper.eq(map.get(key) != null, key, map.get(key));
        }
        Page<Article> pageData = new Page<>(page, size);
        List<Article> list = articleMapper.selectPage(pageData, wrapper);
        pageData.setRecords(list);
        return pageData;
    }

    @Override
    public List<Article> list() {
        List<Article> list = articleMapper.selectList(null);
        return list;
    }
}
