package com.wgmao.article.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.wgmao.article.config.TokenDecode;
import com.wgmao.article.pojo.Article;
import com.wgmao.article.service.ArticleService;
import com.wgmao.entity.PageResult;
import com.wgmao.entity.Result;
import com.wgmao.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @description:
 * @author: zyxyz zhongyue314@163.com
 * @date: 2020/1/11
 */
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TokenDecode tokenDecode;

    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/thumbup/{articleId}")
    public Result thumbup(@PathVariable("articleId") String articleId) {
        //获取点赞用户名
        String userId = tokenDecode.getUserInfo().get("id");
        //查询用户的点赞信息
        String key = "thumbup_article_" + userId + "_" + articleId;
        Object flag = redisTemplate.opsForValue().get(key);
        //判断查询的结果是否为空
        if (flag == null) {
            //为空，未点赞
            articleService.thumbup(articleId, userId);
            //点赞成功，保存点赞信息
            redisTemplate.opsForValue().set(key, 1);

            return new Result(true, StatusCode.OK, "点赞成功");
        } else {
            //不为空，表示已经点过赞
            return new Result(false, StatusCode.REPERROR, "不能重复点赞");
        }
    }

    /**
     * 根据用户id，文章id，建立关注/订阅
     *
     * @param map
     * @return
     */
    @PostMapping("/subscribe")
    public Result subscribe(@RequestBody Map map) {
        //查询是否已经订阅
        Boolean flag = articleService.subscribe(map.get("articleId").toString(), map.get("userId").toString());
        if (flag == true) {
            return new Result(true, StatusCode.OK, "订阅成功");
        } else {
            return new Result(false, StatusCode.OK, "取消订阅成功");
        }
    }

    @GetMapping("/list")
    public List<Article> findAll() {
        return articleService.findAll();
    }

//    @GetMapping
//    public List<Article> list() {
//        return articleService.list();
//    }

    @GetMapping("/articleContent/{articleId}")
    public Map selectArticleRecommend(@PathVariable("articleId") String articleId) {
        Map map = articleService.selectArticleRecommend(articleId);
        return map;
    }

    @GetMapping("/{articleId}")
    public Article findById(@PathVariable("articleId") String articleId) {
        return articleService.findById(articleId);
    }

    @PutMapping
    public Result save(@RequestBody Article article) {
        articleService.save(article);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @PostMapping
    public Result updateById(@RequestBody Article article) {
        articleService.updateById(article);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @DeleteMapping("{articleId}")
    public Result deleteById(@PathVariable("articleId") String articleId) {
        articleService.deleteById(articleId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @GetMapping("/page/{page}/{size}")
    public Result findByPage(@RequestBody Map<String, Object> map, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Article> articlePage = articleService.findByPage(map, page, size);
        PageResult<Article> pageResult = new PageResult<>(
                articlePage.getTotal(), articlePage.getRecords()
        );
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }
}
