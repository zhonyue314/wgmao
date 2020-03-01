package com.wgmao.article.service;

import com.wgmao.article.pojo.Comment;

import java.util.List;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/3/1 10:12
 */
public interface CommentService {
    List<Comment> findAll();

    Comment findById(String id);

    void save(Comment comment);

    void updateById(Comment comment);

    void deleteById(String commentId);

    void thumbup(String commentId);

    List<Comment> findByArticleId(String articleId);
}
