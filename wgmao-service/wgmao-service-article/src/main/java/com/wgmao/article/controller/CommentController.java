package com.wgmao.article.controller;

import com.wgmao.article.config.TokenDecode;
import com.wgmao.article.pojo.Comment;
import com.wgmao.article.service.CommentService;
import com.wgmao.entity.Result;
import com.wgmao.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/3/1 10:49
 */
@RestController
@RequestMapping("/comment")
//@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private TokenDecode tokenDecode;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据评论id点赞
     *
     * @param commentId
     * @return
     */
    @PutMapping("/thumbup/{commentId}")
    public Result thumbup(@PathVariable("commentId") String commentId) {
        //获取用户id
        String userId = tokenDecode.getUserInfo().get("id");
        //查询是否已经点赞
        Object flag = redisTemplate.opsForValue().get("thumbup_" + userId + "_" + commentId);
        if (flag == null) {
            //点赞
            commentService.thumbup(commentId);
            //保存点赞信息
            redisTemplate.opsForValue().set("thumbup_" + userId + "_" + commentId, 1);

            return new Result(true, StatusCode.OK, "点赞成功");
        }
        return new Result(false, StatusCode.REPERROR, "不能重复点赞");
    }

    /**
     * 根据文章id，查询文章评论
     *
     * @param articleId
     * @return
     */
    @GetMapping("/article/{articleId}")
    public Result findByArticleId(@PathVariable String articleId) {
        List<Comment> commentList = commentService.findByArticleId(articleId);
        return new Result(true, StatusCode.OK, "查询成功", commentList);
    }

    /**
     * 根据评论id查询评论
     *
     * @param commentId
     * @return
     */
    @GetMapping("/{commentId}")
    public Result findById(@PathVariable String commentId) {
        Comment comment = commentService.findById(commentId);
        return new Result(true, StatusCode.OK, "查询成功", comment);
    }

    @PostMapping
    public Result save(@RequestBody Comment comment) {
        commentService.save(comment);
        return new Result(true, StatusCode.OK, "评论成功");
    }

    /**
     * 修改评论
     *
     * @param commentId
     * @param comment
     * @return
     */
    @PutMapping("/{commentId}")
    public Result updateById(@PathVariable String commentId, @RequestBody Comment comment) {
        comment.set_id(commentId);
        commentService.updateById(comment);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 根据评论id删除评论
     *
     * @param commentId
     * @return
     */
    @DeleteMapping("/{commentId}")
    public Result deleteById(@PathVariable String commentId) {
        commentService.deleteById(commentId);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
