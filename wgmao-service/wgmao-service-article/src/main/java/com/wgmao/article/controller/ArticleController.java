package com.wgmao.article.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.wgmao.article.pojo.Article;
import com.wgmao.article.service.ArticleService;
import com.wgmao.entity.PageResult;
import com.wgmao.entity.Result;
import com.wgmao.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("/list")
    public List<Article> findAll() {
        List<Article> articleList = articleService.findAll();
        return articleList;
    }

    @GetMapping
    public Result list() {
        List<Article> list = articleService.list();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @GetMapping("/{articleId}")
    public Result<Article> findById(@PathVariable("articleId") String articleId) {
        Article article = articleService.findById(articleId);
        return new Result<>(true, StatusCode.OK, "查询成功", article);
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
