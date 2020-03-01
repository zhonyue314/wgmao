package com.wgmao.article.service.impl;

import com.wgmao.article.pojo.Comment;
import com.wgmao.article.repository.CommentRepository;
import com.wgmao.article.service.CommentService;
import com.wgmao.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/3/1 10:13
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    public CommentRepository commentRepository;

    @Autowired
    public IdWorker idWorker;

    /**
     * 查询所有评论
     *
     * @return
     */
    @Override
    public List<Comment> findAll() {
        List<Comment> commentList = commentRepository.findAll();
        return commentList;
    }

    /**
     * 根据评论id查询
     *
     * @param id
     * @return
     */
    @Override
    public Comment findById(String id) {
        Comment comment = commentRepository.findById(id).get();
        return comment;
    }

    /**
     * 初始化评论数据
     *
     * @param comment
     */
    @Override
    public void save(Comment comment) {
        String id = idWorker.nextId() + "";
        comment.set_id(id);
        //初始化数据
        comment.setPublishdate(new Date());
        comment.setThumbup(0);
        commentRepository.save(comment);
    }

    /**
     * 修改评论
     *
     * @param comment
     */
    @Override
    public void updateById(Comment comment) {
        commentRepository.save(comment);
    }

    /**
     * 根据id删除评论
     *
     * @param commentId
     */
    @Override
    public void deleteById(String commentId) {
        commentRepository.deleteById(commentId);
    }

    /**
     * 点赞
     *
     * @param commentId
     */
    @Override
    public void thumbup(String commentId) {

    }

    /**
     * 根据文章id查询评论数据
     * @param articleId
     * @return
     */
    public List<Comment> findByArticleId(String articleId) {
        List<Comment> list = commentRepository.findByArticleid(articleId);
        return list;
    }
}
